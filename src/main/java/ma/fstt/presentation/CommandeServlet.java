package ma.fstt.presentation;

import ma.fstt.dao.ClientDaoImpl;
import ma.fstt.dao.CommandeDaoImpl;
import ma.fstt.dao.ProduitDaoImpl;
import ma.fstt.entities.Client;
import ma.fstt.entities.Commande;
import ma.fstt.entities.LignedeCommande;
import ma.fstt.entities.Produit;
import ma.fstt.service.IClientDao;
import ma.fstt.service.ICommandeDao;
import ma.fstt.service.IProduitDao;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/commandes")
public class CommandeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    private ICommandeDao commandeDao;

    @Inject
    private IClientDao clientDao;

    @Inject
    private IProduitDao produitDao;

    @Override
    public void init() throws ServletException {
        if (commandeDao == null) commandeDao = new CommandeDaoImpl();
        if (clientDao == null) clientDao = new ClientDaoImpl();
        if (produitDao == null) produitDao = new ProduitDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "new":
                    req.setAttribute("clients", clientDao.findAll());
                    req.setAttribute("produits", produitDao.findDisponibles());
                    req.getRequestDispatcher("/commandes/create.jsp").forward(req, resp);
                    break;
                case "details":
                    Long idDet = Long.parseLong(req.getParameter("id"));
                    Commande cmd = commandeDao.findById(idDet);
                    req.setAttribute("commande", cmd);
                    req.getRequestDispatcher("/commandes/details.jsp").forward(req, resp);
                    break;
                case "delete":
                    Long idDel = Long.parseLong(req.getParameter("id"));
                    commandeDao.delete(idDel);
                    resp.sendRedirect(req.getContextPath() + "/commandes");
                    break;
                case "updateStatut":
                    Long idSt = Long.parseLong(req.getParameter("id"));
                    String st = req.getParameter("statut");
                    commandeDao.updateStatut(idSt, st);
                    resp.sendRedirect(req.getContextPath() + "/commandes?action=details&id=" + idSt);
                    break;
                case "list":
                default:
                    List<Commande> liste = commandeDao.findAll();
                    req.setAttribute("commandes", liste);
                    req.getRequestDispatcher("/commandes/index.jsp").forward(req, resp);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/commandes");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        try {
            String idClientStr = req.getParameter("idClient");
            if (idClientStr == null || idClientStr.trim().isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/commandes?action=new");
                return;
            }

            Long idClient = Long.parseLong(idClientStr);
            Client client = clientDao.findById(idClient);
            Commande commande = new Commande(new Date(), "EN_ATTENTE", client);

            String[] pIds = req.getParameterValues("produitId");
            if (pIds == null) pIds = req.getParameterValues("produitId[]");

            String[] qtes = req.getParameterValues("quantite");
            if (qtes == null) qtes = req.getParameterValues("quantite[]");

            if (pIds != null && qtes != null) {
                for (int i = 0; i < pIds.length; i++) {
                    if (pIds[i] == null || pIds[i].trim().isEmpty()) continue;
                    Long pId = Long.parseLong(pIds[i]);
                    int qte = Integer.parseInt(qtes[i]);
                    if (qte <= 0) continue;

                    Produit p = produitDao.findById(pId);
                    if (p != null) {
                        // Verification de la quantite disponible
                        if (p.getQuantiteStock() < qte) {
                            req.getSession().setAttribute("errorMsg", "Stock insuffisant pour " + p.getDesignation() + " (Stock disponible : " + p.getQuantiteStock() + ")");
                            resp.sendRedirect(req.getContextPath() + "/commandes?action=new");
                            return;
                        }

                        LignedeCommande ligne = new LignedeCommande(qte, p.getPrixUnitaire(), commande, p);
                        commande.ajouterLigne(ligne);
                    }
                }
            }

            if (!commande.getLignes().isEmpty()) {
                commandeDao.save(commande);
                resp.sendRedirect(req.getContextPath() + "/commandes?action=details&id=" + commande.getIdCommande());
            } else {
                resp.sendRedirect(req.getContextPath() + "/commandes?action=new");
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.getSession().setAttribute("errorMsg", "Erreur lors de l'enregistrement : " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/commandes?action=new");
        }
    }
}