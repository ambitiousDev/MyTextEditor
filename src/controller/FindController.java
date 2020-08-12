/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import view.FindForm;
import view.MainForm;

/**
 *
 * @author Saber
 */
public class FindController {

    public FindController(MainForm form) {
        FindForm find = new FindForm(form, false);
        Find f = new Find();
        find.setLocationRelativeTo(form);
        find.setVisible(true);
        find.setTitle("Find");
        JTextField findField = find.getjTextField1();
        JButton findBtn = find.getjButton1();
        find.getjRadioButton2().setSelected(true);
        findBtn.setEnabled(false);
        findBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.find(findField, form.getjTextArea1(), find.getjRadioButton1(), find.getjCheckBox2());
            }

        });
        find.getjButton2().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                find.dispose();
            }
        });
        findField.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                if (findField.getText().isEmpty()) {
                    find.getjButton1().setEnabled(false);
                } else {
                    find.getjButton1().setEnabled(true);
                }
            }
        });

    }

}
