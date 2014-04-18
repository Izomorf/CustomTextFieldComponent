/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.japo.java.swing.samples;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicTextFieldUI;
/**
 *
 * @author ruinmaxk
 */

public class CustomTextFieldComponent extends JTextField implements FocusListener {

    private Icon icon;
    
    private final String hint;
    private boolean showingHint;
    
    public CustomTextFieldComponent () {
        this("");
    }
    
    public CustomTextFieldComponent (final String hint){
        super(hint);
        
        icon = new ImageIcon();
        this.hint = hint;
        this.showingHint = true;
        super.addFocusListener(this);
    }
    
    @Override
    public void focusGained(FocusEvent e) {
        if (this.getText().isEmpty()) {
            super.setText("");
            showingHint = false;
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (this.getText().isEmpty()) {
            super.setText(hint);
            showingHint = true;
        }
    }
 
    @Override
    public String getText() {
        return showingHint ? "" : super.getText();
    }

    /**
     * @return the icon
     */
    public Icon getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    class HintTextFieldUI extends BasicTextFieldUI {
        private String hint;
        private boolean hideOnFocus;
        private Color color;
        
        public Color getColor () {
            return color;
        }
        
        public void setColor (Color color) {
            this.color = color;
            repaint();
        }
    }
}

