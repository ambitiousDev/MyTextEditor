/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Saber
 */
public class Find {

    public void replace(JTextField findField, JTextField replaceField, JTextArea doc, JCheckBox wrap) {
        String findText = findField.getText();
        String replaceText = replaceField.getText();
        //check if any text is selected and selected text is equal to findtext
        if (doc.getSelectedText() != null && doc.getSelectedText().equals(findText)) {
            doc.replaceSelection(replaceText);
        }
        find(findField, doc, null, wrap);
    }

    public void find(JTextField fField, JTextArea doc, JRadioButton up, JCheckBox wrap) {
        String findText = fField.getText();
        int startPost;
        int pos = -1;
        //check if current doc contain text need to find
        if (isExist(doc, findText)) {
            //check if find text in upward or downward direction
            if (up == null || !up.isSelected()) {
                startPost = doc.getSelectionEnd();
                pos = doc.getText().indexOf(findText, startPost);
            } else {
                try {
                    startPost = doc.getSelectionStart();
                    String checkTxt = doc.getText(0, startPost);
                    pos = checkTxt.lastIndexOf(findText);
                } catch (BadLocationException ex) {
                    System.out.println(ex);
                }
            }
            //check if found text or not
            if (pos != -1) {
                doc.select(pos, pos + findText.length());
            } else {
                //check if wrap around check box is selected or not
                if (wrap.isSelected()) {
                    setFirstOccurence(up, doc, findText);
                } else {
                    JOptionPane.showMessageDialog(doc, "Cannot find \"" + findText + "\"");
                }
            }
        }
    }

    private void setFirstOccurence(JRadioButton up, JTextArea doc, String findText) {
        //check if find findText in upward or downward direction or null
        if (up == null || !up.isSelected()) {
            doc.setSelectionStart(doc.getText().indexOf(findText));
            doc.setSelectionEnd(doc.getText().indexOf(findText) + findText.length());
        } else {
            doc.setSelectionStart(doc.getText().lastIndexOf(findText));
            doc.setSelectionEnd(doc.getText().lastIndexOf(findText) + findText.length());
        }
    }

    private boolean isExist(JTextArea doc, String txt) {
        //check if text need to find is exist in document
        if (doc.getText().indexOf(txt) == -1) {
            JOptionPane.showMessageDialog(doc, "Cannot find \"" + txt + "\"");
            return false;
        }
        return true;
    }

}
