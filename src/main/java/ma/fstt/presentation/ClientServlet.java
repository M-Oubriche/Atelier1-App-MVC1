package ma.fstt.presentation;

import ma.fstt.dao.ClientDaoImpl;
import ma.fstt.entities.Client;
import ma.fstt.service.IClientDao;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/clients")
public class ClientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    private IClientDao clientDao;

    @Override
    public void init() throws ServletException {
        if (clientDao == null) {
            clientDao = new ClientDaoImpl();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "new":
                    req.setAttribute("client", new Client());
                    req.getRequestDispatcher("/clients/form.jsp").forward(req, resp);
                    break;
                case "edit":
                    Long idEdit = Long.parseLong(req.getParameter("id"));
                    Client c = clientDao.findById(idEdit);
                    req.setAttribute("client", c);
                    req.getRequestDispatcher("/clients/form.jsp").forward(req, resp);
                    break;
                case "delete":
                    Long idDel = Long.parseLong(req.getParameter("id"));
                    clientDao.delete(idDel);
                    resp.sendRedirect(req.getContextPath() + "/clients");
                    break;
                case "list":
                default:
                    List<Client> liste = clientDao.findAll();
                    req.setAttribute("clients", liste);
                    req.getRequestDispatcher("/clients/index.jsp").forward(req, resp);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String idStr = req.getParameter("idClient");
        String nom = req.getParameter("nom");
        String prenom = req.getParameter("prenom");
        String email = req.getParameter("email");
        String telephone = req.getParameter("telephone");
        String adresse = req.getParameter("adresse");

        Client client = new Client(nom, prenom, email, telephone, adresse);

        try {
            if (idStr != null && !idStr.trim().isEmpty()) {
                client.setIdClient(Long.parseLong(idStr));
                clientDao.update(client);
            } else {
                clientDao.save(client);
            }
            resp.sendRedirect(req.getContextPath() + "/clients");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}