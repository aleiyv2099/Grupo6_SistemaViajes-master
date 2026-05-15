/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaagenciaviajesgrupo6;

import Vista.Login;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.EventQueue;
/**
 *
 * @author Mini Wernaso
 */
public class SistemaAgenciaViajesGrupo6 {
     public static void main(String[] args) {
        // Configure FlatDarkLaf theme BEFORE creating any components
        try {
            // Setup FlatDarkLaf
            FlatDarkLaf.setup();
            
            // Custom Professional Dark Theme
            UIManager.put("Button.arc", 12);
            UIManager.put("Component.arc", 12);
            UIManager.put("TextComponent.arc", 12);
            UIManager.put("TabbedPane.tabAreaInsets", new java.awt.Insets(5, 10, 5, 10));
            
            // Dark backgrounds
            UIManager.put("Panel.background", new java.awt.Color(30, 30, 35));
            UIManager.put("Button.background", new java.awt.Color(45, 85, 135));
            UIManager.put("Button.hoverBackground", new java.awt.Color(55, 95, 145));
            UIManager.put("Button.pressedBackground", new java.awt.Color(35, 75, 125));
            UIManager.put("TextField.background", new java.awt.Color(40, 40, 45));
            UIManager.put("PasswordField.background", new java.awt.Color(40, 40, 45));
            UIManager.put("ComboBox.background", new java.awt.Color(40, 40, 45));
            UIManager.put("Table.background", new java.awt.Color(35, 35, 40));
            UIManager.put("Table.alternateRowColor", new java.awt.Color(38, 38, 43));
            UIManager.put("TableHeader.background", new java.awt.Color(45, 48, 53));
            UIManager.put("TabbedPane.background", new java.awt.Color(30, 30, 35));
            UIManager.put("TabbedPane.selectedBackground", new java.awt.Color(40, 40, 45));
            
            // Text colors
            UIManager.put("Label.foreground", new java.awt.Color(220, 220, 225));
            UIManager.put("TextField.foreground", new java.awt.Color(240, 240, 245));
            UIManager.put("PasswordField.foreground", new java.awt.Color(240, 240, 245));
            UIManager.put("Button.foreground", new java.awt.Color(255, 255, 255));
            UIManager.put("Table.foreground", new java.awt.Color(230, 230, 235));
            UIManager.put("TableHeader.foreground", new java.awt.Color(240, 240, 245));
            UIManager.put("TabbedPane.foreground", new java.awt.Color(220, 220, 225));
            
            // Borders
            UIManager.put("Component.borderColor", new java.awt.Color(60, 63, 70));
            UIManager.put("Component.focusedBorderColor", new java.awt.Color(70, 110, 160));
            UIManager.put("TextField.selectionBackground", new java.awt.Color(70, 110, 160));
            UIManager.put("Table.selectionBackground", new java.awt.Color(60, 100, 150));
            UIManager.put("Table.gridColor", new java.awt.Color(50, 53, 58));
            
        } catch (Exception ex) {
            System.err.println("Error al aplicar FlatLaf: " + ex.getMessage());
            ex.printStackTrace();
        }
        
        // Create and show the login window on the Event Dispatch Thread
        EventQueue.invokeLater(() -> {
            Login lg = new Login();
            lg.setVisible(true);
        });
    }
}
