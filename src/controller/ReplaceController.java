/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import view.MainForm;
import view.ReplaceForm;

/**
 *
 * @author Saber
 */
public class ReplaceController {

    public ReplaceController(MainForm form) {
        ReplaceForm reForm = new ReplaceForm(form, false);
        reForm.setVisible(true);
        reForm.setTitle("Replace");
        reForm.setLocationRelativeTo(form);
        Find f = new Find();
        JTextField findField = reForm.getjTextField1();
        JTextField replaceField = reForm.getjTextField2();
        JButton findNextBtn = reForm.getjButton1();
        JButton replaceBtn = reForm.getjButton2();
        JButton replaceAllBtn = reForm.getjButton3();
        JButton cancelBtn = reForm.getjButton4();
        findNextBtn.setEnabled(false);
        replaceBtn.setEnabled(false);
        replaceAllBtn.setEnabled(false);

        JTextArea doc = form.getjTextArea1();

        findField.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                //check if text fied is empty or not
                if (findField.getText().trim().isEmpty()) {
                    findNextBtn.setEnabled(false);
                    replaceBtn.setEnabled(false);
                    replaceAllBtn.setEnabled(false);
                } else {
                    findNextBtn.setEnabled(true);
                    replaceBtn.setEnabled(true);
                    replaceAllBtn.setEnabled(true);
                }
            }
        });

        findNextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.find(findField, doc, null, reForm.getjCheckBox2());
            }
        });

        replaceAllBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //check if replace field is empty or not
                if (!replaceField.getText().trim().isEmpty()) {
                    String curr = doc.getText();
                    String newdoc = curr.replaceAll(findField.getText(), replaceField.getText());
                    doc.setText(newdoc);
                } else {
                    JOptionPane.showMessageDialog(reForm, "Please enter replace text");
                }
            }
        });

        replaceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //check if replace field is empty
                if (replaceField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(reForm, "Please Enter Replace Text");
                    return;
                }
                f.replace(findField, replaceField, doc, reForm.getjCheckBox2());
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reForm.setVisible(false);
            }
        });

    }

}
