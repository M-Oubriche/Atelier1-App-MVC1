<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/includes/header.jsp"/>

<div class="text-center my-5">
    <h2 class="mb-3">Gestion des Commandes</h2>
    <p class="text-muted mb-5">Gestion des clients, des produits et des commandes</p>

    <div class="row justify-content-center g-4">
        <div class="col-md-3">
            <div class="border p-4 rounded text-center">
                <h5>Clients</h5>
                <p class="text-muted small">Gestion du fichier client</p>
                <a href="${pageContext.request.contextPath}/clients" class="btn btn-outline-dark w-100">Acceder</a>
            </div>
        </div>
        <div class="col-md-3">
            <div class="border p-4 rounded text-center">
                <h5>Produits</h5>
                <p class="text-muted small">Gestion du catalogue</p>
                <a href="${pageContext.request.contextPath}/produits" class="btn btn-outline-dark w-100">Acceder</a>
            </div>
        </div>
        <div class="col-md-3">
            <div class="border p-4 rounded text-center">
                <h5>Commandes</h5>
                <p class="text-muted small">Passer et lister les commandes</p>
                <a href="${pageContext.request.contextPath}/commandes" class="btn btn-outline-dark w-100">Acceder</a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/includes/footer.jsp"/>