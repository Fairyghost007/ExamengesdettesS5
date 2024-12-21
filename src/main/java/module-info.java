module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires java.persistence;
    requires org.yaml.snakeyaml;
    requires lombok;
    requires de.jensd.fx.glyphs.commons;
    requires de.jensd.fx.glyphs.fontawesome;
    requires org.postgresql.jdbc;
    requires org.hibernate.orm.core;
    requires javafx.graphics;
    // requires us.ihmc.core;
    requires com.jfoenix;
    requires java.desktop;
    requires javafx.base;


    opens com.example to javafx.fxml;
    opens com.example.controllers to javafx.fxml;
    opens com.example.controllers.Client to javafx.fxml; 
    opens com.example.controllers.Boutiquier to javafx.fxml;
    opens com.example.controllers.Admin to javafx.fxml;
    opens com.example.entities to org.hibernate.orm.core;

    exports com.example;
    exports com.example.entities;
    exports com.example.controllers;
    exports com.example.controllers.Client;
    exports com.example.controllers.Boutiquier;
    exports com.example.controllers.Admin;
    exports com.example.Models;
}
