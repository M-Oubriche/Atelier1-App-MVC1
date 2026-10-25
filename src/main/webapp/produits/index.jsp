<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<jsp:include page="/includes/header.jsp"/>

<div class="text-center mb-4">
    <h3>Catalogue des Produits</h3>
    <a href="${pageContext.request.contextPath}/produits?action=new" class="btn btn-outline-dark mt-2">Ajouter un Produit</a>
</div>

<div class="table-responsive">
    <table class="table table-bordered text-center align-middle">
        <thead>
            <tr>
                <th>ID</th>
                <th>Designation</th>
                <th>Prix (DH)</th>
                <th>Stock</th>
                <th>Description</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="p" items="${produits}">
                <tr>
                    <td>${p.idProduit}</td>
                    <td>${p.designation}</td>
                    <td>${p.prixUnitaire} DH</td>
                    <td>${p.quantiteStock}</td>
                    <td>${p.description}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/produits?action=edit&id=${p.idProduit}" class="btn btn-sm btn-outline-dark me-1">Modifier</a>
                        <a href="${pageContext.request.contextPath}/produits?action=delete&id=${p.idProduit}" class="btn btn-sm btn-outline-dark" onclick="return confirm('Supprimer ce produit ?');">Supprimer</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<jsp:include page="/includes/footer.jsp"/>