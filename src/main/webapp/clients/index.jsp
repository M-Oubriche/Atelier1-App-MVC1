<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<jsp:include page="/includes/header.jsp"/>

<div class="text-center mb-4">
    <h3>Liste des Clients</h3>
    <a href="${pageContext.request.contextPath}/clients?action=new" class="btn btn-outline-dark mt-2">Ajouter un Client</a>
</div>

<div class="table-responsive">
    <table class="table table-bordered text-center align-middle">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Prenom</th>
                <th>Email</th>
                <th>Telephone</th>
                <th>Adresse</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="c" items="${clients}">
                <tr>
                    <td>${c.idClient}</td>
                    <td>${c.nom}</td>
                    <td>${c.prenom}</td>
                    <td>${c.email}</td>
                    <td>${c.telephone}</td>
                    <td>${c.adresse}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/clients?action=edit&id=${c.idClient}" class="btn btn-sm btn-outline-dark me-1">Modifier</a>
                        <a href="${pageContext.request.contextPath}/clients?action=delete&id=${c.idClient}" class="btn btn-sm btn-outline-dark" onclick="return confirm('Supprimer ce client ?');">Supprimer</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<jsp:include page="/includes/footer.jsp"/>