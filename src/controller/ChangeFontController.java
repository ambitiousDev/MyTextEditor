/*
 * To changeForm this license header, choose License Headers in Project Properties.
 * To changeForm this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import view.ChangeFontForm;
import view.MainForm;

/**
 *
 * @author Saber
 */
public class ChangeFontController {

    public ChangeFontController(MainForm form) {
        ChangeFontForm changeForm = new ChangeFontForm(form, false);
        changeForm.setVisible(true);
        JList fontList = changeForm.getFontList();
        JList styleList = changeForm.getFontStyleList();
        JList sizeList = changeForm.getFontSizeList();
        JTextField fontField = changeForm.getjTextField1();
        JTextField styleField = changeForm.getjTextField2();
        JTextField sizeField = changeForm.getjTextField3();
        JTextArea doc = form.getjTextArea1();

        changeForm.setTitle("Change Font");
        String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontList.setListData(fonts);
        String styles[] = {"Regular", "Bold", "Italic", "Bold Italic"};
        styleList.setListData(styles);
        String sizes[] = {"8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "36", "40", "72"};
        sizeList.setListData(sizes);

        Font font = doc.getFont();
        String curFont = font.getFamily();
        int curStyle = font.getStyle();
        int curSize = font.getSize();

        fontList.setSelectedValue(curFont,true);
        styleList.setSelectedIndex(curStyle);
        sizeList.setSelectedValue(Integer.toString(curSize), true);
        fontField.setText(curFont);
        styleField.setText(styleList.getSelectedValue().toString());
        sizeField.setText(Integer.toString(curSize));

        JLabel sample = changeForm.getSampleLabel();

        fontList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String fontChoose = fontList.getSelectedValue().toString();
                Font sampleFont = sample.getFont();
                int styleCurrent = sampleFont.getStyle();
                int sizeCurrent = sampleFont.getSize();
                fontField.setText(fontChoose);
                sample.setFont(new Font(fontChoose, styleCurrent, sizeCurrent));
            }
        });

        styleList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Font sampleFont = sample.getFont();
                int styleChoose = styleList.getSelectedIndex();
                String fontCurrent = sampleFont.getFontName();
                int sizeCurrent = sampleFont.getSize();
                //change font style base on user choice
                switch (styleChoose) {
                    case Font.PLAIN:
                        styleField.setText("Regular");
                        break;
                    case Font.BOLD:
                        styleField.setText("Bold");
                        break;
                    case Font.ITALIC:
                        styleField.setText("Italic");
                        break;
                    case Font.BOLD + Font.ITALIC:
                        styleField.setText("Bold Italic");
                        break;
                }
                sample.setFont(new Font(fontCurrent, styleChoose, sizeCurrent));
            }
        });

        sizeList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Font sampleFont = sample.getFont();
                String sizeChoose = sizeList.getSelectedValue().toString();
                String fontCurrent = sampleFont.getFontName();
                int styleCurrent = sampleFont.getStyle();
                sizeField.setText(sizeChoose);
                sample.setFont(new Font(fontCurrent, styleCurrent, Integer.parseInt(sizeChoose)));
            }
        });
        sizeField.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                String sizeChoose = sizeField.getText();
                //check if text match decimal pattern
                if (sizeChoose.matches("[0-9]+")) {
                    Font sampleFont = sample.getFont();
                    String fontCurrent = sampleFont.getFontName();
                    int styleCurrent = sampleFont.getStyle();
                    sample.setFont(new Font(fontCurrent, styleCurrent, Integer.parseInt(sizeChoose)));
                }
            }
        });

        changeForm.getjButton1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newFont = fontList.getSelectedValue().toString();
                int newStyle = styleList.getSelectedIndex();
                int newSize = Integer.parseInt(sizeField.getText());
                doc.setFont(new Font(newFont, newStyle, newSize));
                changeForm.setVisible(false);
            }
        });
        changeForm.getjButton2().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeForm.setVisible(false);
            }
        });
    }

}
