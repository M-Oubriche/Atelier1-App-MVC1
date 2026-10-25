<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<jsp:include page="/includes/header.jsp"/>

<div class="row justify-content-center">
    <div class="col-md-8 text-center">
        <h3 class="mb-4">Details de la Commande #${commande.idCommande}</h3>

        <div class="border p-4 rounded mb-4 text-start">
            <div class="d-flex justify-content-between mb-3 border-bottom pb-2">
                <span><strong>Date :</strong> <fmt:formatDate value="${commande.dateCommande}" pattern="dd/MM/yyyy HH:mm"/></span>
                <span><strong>Statut :</strong> ${commande.statut}</span>
            </div>
            <p class="mb-1"><strong>Client :</strong> ${commande.client.nomComplet}</p>
            <p class="mb-1"><strong>Email :</strong> ${commande.client.email}</p>
            <p class="mb-1"><strong>Telephone :</strong> ${commande.client.telephone}</p>
            <p class="mb-0"><strong>Adresse :</strong> ${commande.client.adresse}</p>
        </div>

        <div class="table-responsive mb-4">
            <table class="table table-bordered text-center align-middle">
                <thead>
                    <tr>
                        <th>Produit</th>
                        <th>Quantite</th>
                        <th>Prix Unitaire</th>
                        <th>Sous-total</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="l" items="${commande.lignes}">
                        <tr>
                            <td>${l.produit.designation}</td>
                            <td>${l.quantite}</td>
                            <td>${l.prixVente} DH</td>
                            <td>${l.sousTotal} DH</td>
                        </tr>
                    </c:forEach>
                </tbody>
                <tfoot>
                    <tr>
                        <th colspan="3" class="text-end">Montant Total :</th>
                        <th>${commande.montantTotal} DH</th>
                    </tr>
                </tfoot>
            </table>
        </div>

        <div class="text-center">
            <a href="${pageContext.request.contextPath}/commandes" class="btn btn-outline-secondary px-4 me-2">Retour a la liste</a>
            <a href="${pageContext.request.contextPath}/commandes?action=updateStatut&id=${commande.idCommande}&statut=VALIDEE" class="btn btn-outline-dark px-4 me-2">Marquer comme Validee</a>
            <a href="${pageContext.request.contextPath}/commandes?action=updateStatut&id=${commande.idCommande}&statut=EXPEDIEE" class="btn btn-outline-dark px-4">Marquer comme Expediee</a>
        </div>
    </div>
</div>

<jsp:include page="/includes/footer.jsp"/>