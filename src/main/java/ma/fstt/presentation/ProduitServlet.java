package ma.fstt.presentation;

import ma.fstt.dao.ProduitDaoImpl;
import ma.fstt.entities.Produit;
import ma.fstt.service.IProduitDao;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/produits")
public class ProduitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    private IProduitDao produitDao;

    @Override
    public void init() throws ServletException {
        if (produitDao == null) {
            produitDao = new ProduitDaoImpl();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "new":
                    req.setAttribute("produit", new Produit());
                    req.getRequestDispatcher("/produits/form.jsp").forward(req, resp);
                    break;
                case "edit":
                    Long idEdit = Long.parseLong(req.getParameter("id"));
                    Produit p = produitDao.findById(idEdit);
                    req.setAttribute("produit", p);
                    req.getRequestDispatcher("/produits/form.jsp").forward(req, resp);
                    break;
                case "delete":
                    Long idDel = Long.parseLong(req.getParameter("id"));
                    produitDao.delete(idDel);
                    resp.sendRedirect(req.getContextPath() + "/produits");
                    break;
                case "list":
                default:
                    List<Produit> liste = produitDao.findAll();
                    req.setAttribute("produits", liste);
                    req.getRequestDispatcher("/produits/index.jsp").forward(req, resp);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/produits");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String idStr = req.getParameter("idProduit");
        String designation = req.getParameter("designation");
        String prixStr = req.getParameter("prixUnitaire");
        String stockStr = req.getParameter("quantiteStock");
        String description = req.getParameter("description");

        try {
            double prix = (prixStr != null && !prixStr.trim().isEmpty()) ? Double.parseDouble(prixStr) : 0.0;
            int stock = (stockStr != null && !stockStr.trim().isEmpty()) ? Integer.parseInt(stockStr) : 0;

            Produit produit = new Produit(designation, prix, stock, description);

            if (idStr != null && !idStr.trim().isEmpty()) {
                produit.setIdProduit(Long.parseLong(idStr));
                produitDao.update(produit);
            } else {
                produitDao.save(produit);
            }
            resp.sendRedirect(req.getContextPath() + "/produits");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/produits");
        }
    }
}