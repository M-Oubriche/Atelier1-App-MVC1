<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des Commandes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container justify-content-center">
        <a class="navbar-brand me-4" href="${pageContext.request.contextPath}/">Gestion Commandes</a>
        <div class="d-flex">
            <ul class="navbar-nav flex-row gap-3">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/clients">Clients</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/produits">Produits</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/commandes">Commandes</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="container">