-- Base de donnees : db_commandes
-- Application Web JEE : Gestion des Commandes (Architecture MVC 1)

CREATE DATABASE IF NOT EXISTS `db_commandes` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `db_commandes`;

-- Desactivation temporaire des verifications de cles etrangeres pour le nettoyage
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `ligne_commande`;
DROP TABLE IF EXISTS `commande`;
DROP TABLE IF EXISTS `produit`;
DROP TABLE IF EXISTS `client`;
SET FOREIGN_KEY_CHECKS = 1;

-- Table : client
CREATE TABLE `client` (
    `id_client` BIGINT NOT NULL AUTO_INCREMENT,
    `nom` VARCHAR(100) NOT NULL,
    `prenom` VARCHAR(100) NOT NULL,
    `email` VARCHAR(150) NOT NULL,
    `telephone` VARCHAR(30) DEFAULT NULL,
    `adresse` TEXT DEFAULT NULL,
    PRIMARY KEY (`id_client`),
    UNIQUE KEY `uk_client_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table : produit
CREATE TABLE `produit` (
    `id_produit` BIGINT NOT NULL AUTO_INCREMENT,
    `designation` VARCHAR(200) NOT NULL,
    `prix_unitaire` DECIMAL(10,2) NOT NULL,
    `quantite_stock` INT NOT NULL,
    `description` TEXT DEFAULT NULL,
    PRIMARY KEY (`id_produit`),
    CONSTRAINT `produit_chk_1` CHECK (`prix_unitaire` >= 0),
    CONSTRAINT `produit_chk_2` CHECK (`quantite_stock` >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table : commande
CREATE TABLE `commande` (
    `id_commande` BIGINT NOT NULL AUTO_INCREMENT,
    `date_commande` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `statut` VARCHAR(50) NOT NULL DEFAULT 'EN_ATTENTE',
    `id_client` BIGINT NOT NULL,
    PRIMARY KEY (`id_commande`),
    KEY `idx_commande_client` (`id_client`),
    CONSTRAINT `fk_commande_client` FOREIGN KEY (`id_client`) REFERENCES `client` (`id_client`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table : ligne_commande
CREATE TABLE `ligne_commande` (
    `id_ligne` BIGINT NOT NULL AUTO_INCREMENT,
    `quantite` INT NOT NULL,
    `prix_vente` DECIMAL(10,2) NOT NULL,
    `id_commande` BIGINT NOT NULL,
    `id_produit` BIGINT NOT NULL,
    PRIMARY KEY (`id_ligne`),
    KEY `idx_ligne_commande` (`id_commande`),
    KEY `idx_ligne_produit` (`id_produit`),
    CONSTRAINT `ligne_commande_chk_1` CHECK (`quantite` > 0),
    CONSTRAINT `ligne_commande_chk_2` CHECK (`prix_vente` >= 0),
    CONSTRAINT `fk_ligne_commande` FOREIGN KEY (`id_commande`) REFERENCES `commande` (`id_commande`) ON DELETE CASCADE,
    CONSTRAINT `fk_ligne_produit` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id_produit`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Donnees de demonstration initiales
INSERT INTO `client` (`id_client`, `nom`, `prenom`, `email`, `telephone`, `adresse`) VALUES
(1, 'El Amrani', 'Yassine', 'yassine.elamrani@gmail.com', '0661122334', 'Tanger, Centre Ville'),
(2, 'Benjelloun', 'Sara', 'sara.benjelloun@gmail.com', '0662233445', 'Tanger, Malabata');

-- Produits
INSERT INTO `produit` (`id_produit`, `designation`, `prix_unitaire`, `quantite_stock`, `description`) VALUES
(1, 'PC Portable Dell Latitude 5530', 9500.00, 15, 'Intel Core i7 16Go RAM 512Go SSD'),
(2, 'Souris Ergonomique Sans Fil Logitech', 250.00, 50, 'Capteur optique haute precision'),
(3, 'Ecran 27 Pouces Dell UltraSharp', 3200.00, 20, 'Resolution 4K IPS antireflet'),
(4, 'Clavier Mecanique Retro-eclaire', 650.00, 28, 'Switch Red silencieux'),
(5, 'Disque Dur Externe SSD 1To SanDisk', 1100.00, 1000, 'USB-C ultra rapide 1050 Mo/s');
