/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common;
import java.awt.Component;
import java.awt.Container;
import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.tree.TreePath;
import junit.framework.Assert;
/**
 * Ensemble de classe utilitaire pour les tests IHM.
 */
public final class GuiUtil {
    private GuiUtil() {}

    public static Component findByName(String name, Component comp) {
        if (comp == null || name.equals(comp.getName())) {
            return comp;
        }

        if (comp instanceof Container) {
            Container container = (Container)comp;
            for (int idx = 0; idx < container.getComponentCount(); idx++) {
                Component result = findByName(name, container.getComponent(idx));
                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }


    public static AbstractButton findButtonByLabel(String label, Component comp) {
        if (comp == null) {
            return null;
        }

        if (comp instanceof AbstractButton) {
            AbstractButton button = (AbstractButton)comp;
            if (label.equals(button.getText())) {
                return button;
            }
            else {
                return null;
            }
        }

        if (comp instanceof Container) {
            Container container = (Container)comp;
            for (int idx = 0; idx < container.getComponentCount(); idx++) {
                AbstractButton result = findButtonByLabel(label, container.getComponent(idx));
                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }


    public static JComboBox findComboBoxByName(String name, Component comp) {
        return (JComboBox)findByName(name, comp);
    }


    public static String uiDisplayedContent(JTree tree) {
        StringBuffer result = new StringBuffer();
        int rowCount = tree.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            TreePath path = tree.getPathForRow(i);
            result.append(pathToString(tree, path));
            if (i + 1 < rowCount) {
                result.append(' ');
            }
        }
        return result.toString();
    }


    public static void assertRenderer(String expected, JComboBox comboBox) {
        assertRenderer(expected, comboBox.getModel(), comboBox.getRenderer());
    }


    public static void assertRenderer(String expected, JList jList) {
        assertRenderer(expected, jList.getModel(), jList.getCellRenderer());
    }


    private static void assertRenderer(String expected, ListModel model, ListCellRenderer renderer) {
        StringBuffer actual = new StringBuffer();
        for (int i = 0; i < model.getSize(); i++) {
            Object modelValue = model.getElementAt(i);
            Component rendereredComponent =
                renderer.getListCellRendererComponent(new JList(), modelValue, i, false, false);
            if (actual.length() > 0) {
                actual.append(", ");
            }
            actual.append(((JLabel)rendereredComponent).getText());
        }
        Assert.assertEquals(expected, actual.toString());
    }


    private static String pathToString(JTree tree, TreePath treePath) {
        StringBuffer buffer = new StringBuffer("[");

        Object[] path = treePath.getPath();

        for (int i = 0; i < path.length; i++) {
            buffer.append(tree.convertValueToText(path[i], false, true, true, i, false));
            if (i + 1 < path.length) {
                buffer.append(", ");
            }
        }
        return buffer.append("]").toString();
    }
}
