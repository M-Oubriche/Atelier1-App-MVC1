<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<jsp:include page="/includes/header.jsp"/>

<div class="row justify-content-center">
    <div class="col-md-6 text-center">
        <h3 class="mb-4">${produit.idProduit != null ? 'Modifier le Produit' : 'Ajouter un Produit'}</h3>

        <div class="border p-4 rounded text-start">
            <form action="${pageContext.request.contextPath}/produits" method="post">
                <input type="hidden" name="idProduit" value="${produit.idProduit}"/>

                <div class="mb-3">
                    <label class="form-label">Designation :</label>
                    <input type="text" name="designation" class="form-control" value="${produit.designation}" required/>
                </div>
                <div class="mb-3">
                    <label class="form-label">Prix Unitaire (DH) :</label>
                    <input type="number" step="0.01" name="prixUnitaire" class="form-control" value="${produit.prixUnitaire}" required/>
                </div>
                <div class="mb-3">
                    <label class="form-label">Quantite en Stock :</label>
                    <input type="number" name="quantiteStock" class="form-control" value="${produit.quantiteStock}" required/>
                </div>
                <div class="mb-3">
                    <label class="form-label">Description :</label>
                    <textarea name="description" class="form-control" rows="2">${produit.description}</textarea>
                </div>

                <div class="text-center mt-4">
                    <button type="submit" class="btn btn-outline-dark px-4 me-2">Enregistrer</button>
                    <a href="${pageContext.request.contextPath}/produits" class="btn btn-outline-secondary px-4">Annuler</a>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="/includes/footer.jsp"/>