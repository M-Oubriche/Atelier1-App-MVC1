<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<jsp:include page="/includes/header.jsp"/>

<div class="row justify-content-center">
    <div class="col-md-7 text-center">
        <h3 class="mb-4">Passer une Nouvelle Commande</h3>

        <c:if test="${not empty sessionScope.errorMsg}">
            <div class="border p-2 mb-3 text-center text-danger">
                ${sessionScope.errorMsg}
            </div>
            <c:remove var="errorMsg" scope="session"/>
        </c:if>

        <div class="border p-4 rounded text-start">
            <form action="${pageContext.request.contextPath}/commandes" method="post">
                
                <div class="mb-4">
                    <label class="form-label fw-bold">Selectionner le Client :</label>
                    <select name="idClient" class="form-select" required>
                        <option value="">-- Choisir un client --</option>
                        <c:forEach var="c" items="${clients}">
                            <option value="${c.idClient}">${c.nomComplet} (${c.email})</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-4">
                    <label class="form-label fw-bold">Produit a commander :</label>
                    <div class="row g-2">
                        <div class="col-8">
                            <select name="produitId" id="produitSelect" class="form-select" onchange="updateMaxStock(this)" required>
                                <option value="" data-stock="1">-- Choisir un produit --</option>
                                <c:forEach var="p" items="${produits}">
                                    <option value="${p.idProduit}" data-stock="${p.quantiteStock}">${p.designation} - ${p.prixUnitaire} DH (Stock: ${p.quantiteStock})</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-4">
                            <input type="number" name="quantite" id="quantiteInput" class="form-control text-center" value="1" min="1" max="1" placeholder="Quantite" required/>
                        </div>
                    </div>
                </div>

                <div class="text-center mt-4">
                    <button type="submit" class="btn btn-outline-dark px-4 me-2">Valider la Commande</button>
                    <a href="${pageContext.request.contextPath}/commandes" class="btn btn-outline-secondary px-4">Annuler</a>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
function updateMaxStock(selectElem) {
    var option = selectElem.options[selectElem.selectedIndex];
    var stock = option.getAttribute('data-stock');
    var input = document.getElementById('quantiteInput');
    if (stock) {
        input.max = stock;
    }
}
</script>

<jsp:include page="/includes/footer.jsp"/>