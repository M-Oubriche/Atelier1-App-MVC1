package ma.fstt.presentation;

import ma.fstt.dao.ProduitDaoImpl;
import ma.fstt.entities.Produit;
import ma.fstt.service.IProduitDao;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
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
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String idStr = req.getParameter("idProduit");
        String designation = req.getParameter("designation");
        double prix = Double.parseDouble(req.getParameter("prixUnitaire"));
        int stock = Integer.parseInt(req.getParameter("quantiteStock"));
        String description = req.getParameter("description");

        Produit produit = new Produit(designation, prix, stock, description);

        try {
            if (idStr != null && !idStr.trim().isEmpty()) {
                produit.setIdProduit(Long.parseLong(idStr));
                produitDao.update(produit);
            } else {
                produitDao.save(produit);
            }
            resp.sendRedirect(req.getContextPath() + "/produits");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}