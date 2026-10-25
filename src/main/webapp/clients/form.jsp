<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<jsp:include page="/includes/header.jsp"/>

<div class="row justify-content-center">
    <div class="col-md-6 text-center">
        <h3 class="mb-4">${client.idClient != null ? 'Modifier le Client' : 'Ajouter un Client'}</h3>

        <div class="border p-4 rounded text-start">
            <form action="${pageContext.request.contextPath}/clients" method="post">
                <input type="hidden" name="idClient" value="${client.idClient}"/>

                <div class="mb-3">
                    <label class="form-label">Nom :</label>
                    <input type="text" name="nom" class="form-control" value="${client.nom}" required/>
                </div>
                <div class="mb-3">
                    <label class="form-label">Prenom :</label>
                    <input type="text" name="prenom" class="form-control" value="${client.prenom}" required/>
                </div>
                <div class="mb-3">
                    <label class="form-label">Email :</label>
                    <input type="email" name="email" class="form-control" value="${client.email}" required/>
                </div>
                <div class="mb-3">
                    <label class="form-label">Telephone :</label>
                    <input type="text" name="telephone" class="form-control" value="${client.telephone}"/>
                </div>
                <div class="mb-3">
                    <label class="form-label">Adresse :</label>
                    <textarea name="adresse" class="form-control" rows="2">${client.adresse}</textarea>
                </div>

                <div class="text-center mt-4">
                    <button type="submit" class="btn btn-outline-dark px-4 me-2">Enregistrer</button>
                    <a href="${pageContext.request.contextPath}/clients" class="btn btn-outline-secondary px-4">Annuler</a>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="/includes/footer.jsp"/>