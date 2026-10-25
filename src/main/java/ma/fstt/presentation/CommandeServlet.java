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

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
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
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Long idClient = Long.parseLong(req.getParameter("idClient"));
        String[] produitIds = req.getParameterValues("produitId[]");
        String[] quantites = req.getParameterValues("quantite[]");

        try {
            Client client = clientDao.findById(idClient);
            Commande commande = new Commande(new Date(), "EN_ATTENTE", client);

            if (produitIds != null && quantites != null) {
                for (int i = 0; i < produitIds.length; i++) {
                    if (produitIds[i] == null || produitIds[i].trim().isEmpty()) continue;
                    Long pId = Long.parseLong(produitIds[i]);
                    int qte = Integer.parseInt(quantites[i]);
                    if (qte <= 0) continue;

                    Produit p = produitDao.findById(pId);
                    if (p != null) {
                        LignedeCommande ligne = new LignedeCommande(qte, p.getPrixUnitaire(), commande, p);
                        commande.ajouterLigne(ligne);
                    }
                }
            }

            commandeDao.save(commande);
            resp.sendRedirect(req.getContextPath() + "/commandes?action=details&id=" + commande.getIdCommande());
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}