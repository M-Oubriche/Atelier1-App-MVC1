<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<jsp:include page="/includes/header.jsp"/>

<div class="text-center mb-4">
    <h3>Gestion des Commandes</h3>
    <a href="${pageContext.request.contextPath}/commandes?action=new" class="btn btn-outline-dark mt-2">Nouvelle Commande</a>
</div>

<div class="table-responsive">
    <table class="table table-bordered text-center align-middle">
        <thead>
            <tr>
                <th>Numero</th>
                <th>Date</th>
                <th>Client</th>
                <th>Statut</th>
                <th>Total (DH)</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="cmd" items="${commandes}">
                <tr>
                    <td>#${cmd.idCommande}</td>
                    <td><fmt:formatDate value="${cmd.dateCommande}" pattern="dd/MM/yyyy HH:mm"/></td>
                    <td>${cmd.client.nomComplet}</td>
                    <td>${cmd.statut}</td>
                    <td>${cmd.montantTotal} DH</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/commandes?action=details&id=${cmd.idCommande}" class="btn btn-sm btn-outline-dark me-1">Details</a>
                        <a href="${pageContext.request.contextPath}/commandes?action=delete&id=${cmd.idCommande}" class="btn btn-sm btn-outline-dark" onclick="return confirm('Supprimer cette commande ?');">Supprimer</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty commandes}">
                <tr>
                    <td colspan="6" class="text-center text-muted py-3">Aucune commande enregistree.</td>
                </tr>
            </c:if>
        </tbody>
    </table>
</div>

<jsp:include page="/includes/footer.jsp"/>