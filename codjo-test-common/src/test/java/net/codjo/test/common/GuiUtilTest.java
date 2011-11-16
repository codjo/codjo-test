/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
/**
 * Classe de test de {@link net.codjo.test.common.GuiUtil}.
 */
public class GuiUtilTest extends TestCase {
    public void test_findByName() throws Exception {
        JPanel panel = new JPanel();
        JLabel expected = new JLabel();
        expected.setName("myName");
        panel.add(expected);
        assertSame(expected, GuiUtil.findByName("myName", panel));
    }


    public void test_findButtonByLabel() throws Exception {
        JPanel panel = new JPanel();
        JButton expected = new JButton("mon label");
        expected.setName("myName");
        panel.add(expected);
        assertSame(expected, GuiUtil.findButtonByLabel("mon label", panel));
    }


    public void test_uiDisplayedContent() throws Exception {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Racine");
        root.add(new DefaultMutableTreeNode("noeud"));
        assertEquals("[Racine] [Racine, noeud]", GuiUtil.uiDisplayedContent(new JTree(root)));
    }


    public void test_findComboBoxByName() throws Exception {
        JPanel panel = new JPanel();
        JComboBox expected = new JComboBox();
        expected.setName("myName");
        panel.add(expected);
        assertSame(expected, GuiUtil.findComboBoxByName("myName", panel));
    }


    public void test_assertRenderer_combobox() throws Exception {
        JComboBox expected = new JComboBox(new Object[] {"1", "2"});
        expected.setRenderer(new DefaultListCellRenderer() {
                @Override
                public void setText(String text) {
                    super.setText("row" + text);
                }
            });
        GuiUtil.assertRenderer("row1, row2", expected);

        try {
            GuiUtil.assertRenderer("baddd", expected);
            throw new Error("Test en echec");
        }
        catch (AssertionFailedError ex) {
            ; // Ok
        }
    }


    public void test_assertRenderer_list() throws Exception {
        JList expected = new JList(new Object[] {"1", "2"});
        expected.setCellRenderer(new DefaultListCellRenderer() {
                @Override
                public void setText(String text) {
                    super.setText("row" + text);
                }
            });

        GuiUtil.assertRenderer("row1, row2", expected);

        try {
            GuiUtil.assertRenderer("baddd", expected);
            throw new Error("Test en echec");
        }
        catch (AssertionFailedError ex) {
            ; // Ok
        }
    }
}
