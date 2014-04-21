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
import javax.swing.text.JTextComponent;
/**
 *
 * @author ruinmaxk
 */

public class CustomTextFieldComponent extends JTextField implements FocusListener {

    private Icon icon;
    
    private String hint;
    private Color hintColor;
    
    public CustomTextFieldComponent () {
        this("");
    }
    
    public CustomTextFieldComponent (String hint) {
        this("", null);
    }
    
    public CustomTextFieldComponent (String hint, Color color){
        super(hint);
        
        icon = new ImageIcon();
        this.hint = hint;
        this.hintColor = color;
        
        super.addFocusListener(this);
    }
    
    @Override
    public void focusGained(FocusEvent e) {
        if (this.getText().isEmpty()) {
            super.setText("");
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (this.getText().isEmpty()) {
            setHint(hint);
        }
    }
 
    @Override
    public String getText() {
        String typed = super.getText();
        return typed.equals(hint) ? "" : typed;
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
    
    public void setHint(String hint) {
        this.hint = hint;
        setUI(new HintTextFieldUI(hint, false, hintColor));
    }

    public void setHintColor(Color color) {
        this.hintColor = color;
        if (getUI() instanceof HintTextFieldUI) {
            ((HintTextFieldUI)getUI()).setColor(color);
        }
        
    }
    
    public Color getHintColor() {
        return hintColor;
    }
    
    class HintTextFieldUI extends BasicTextFieldUI implements FocusListener {
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
        
        public void repaint() {
            if (getComponent() != null) {
                getComponent().repaint();
            }
        }
        
        public boolean isHideOnFocus() {
            return hideOnFocus;
        }
        
        public void setHideOnFocus (boolean hideOnFocus) {
            this.hideOnFocus = hideOnFocus;
            repaint();
        }
        
        public String getHint() {
            return hint;
        }
        
        public void setHint(String hint) {
            this.hint = hint;
            repaint();
        }
        
        public HintTextFieldUI(String hint) {
            this(hint, false);
        }
        
        public HintTextFieldUI(String hint, boolean hideOnFocus) {
            this(hint, hideOnFocus, null);
        }

        public HintTextFieldUI(String hint, boolean hideOnFocus, Color color) {
            this.hint = hint;
            this.hideOnFocus = hideOnFocus;
            this.color = color;
        }
        
        @Override
        protected void paintSafely(Graphics g) {
            super.paintSafely(g);
            JTextComponent comp = getComponent();
            
            if (hint != null && comp.getText().isEmpty() && !(hideOnFocus && comp.hasFocus())) {
                if (color != null) {
                    g.setColor(color);
                } else {
                    g.setColor(Color.GRAY);
                }
                int padding = (comp.getHeight() - comp.getFont().getSize()) / 2;
                g.drawString(hint, 5, comp.getHeight() - padding - 1);
            }
        }
        
        @Override
        public void focusGained(FocusEvent e) {
            if (hideOnFocus) repaint();
        }
        
        @Override
        public void focusLost(FocusEvent e) {
            if (hideOnFocus) repaint();
        }
        
        @Override
        protected void installListeners() {
            super.installListeners();
            getComponent().addFocusListener(this);
        }
        
        @Override
        protected void uninstallListeners() {
            super.uninstallListeners();
            getComponent().removeFocusListener(this);
        }
    }
}
