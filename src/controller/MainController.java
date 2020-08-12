/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.undo.UndoManager;
import view.MainForm;

/**
 *
 * @author Saber
 */
public class MainController {

    MainForm form = new MainForm();
    JTextArea text = form.getjTextArea1();
    JFileChooser chooser = new JFileChooser();
    UndoManager undoManager = new UndoManager();
    File file;
    boolean saved;

    public MainController() {
        form.setLocationRelativeTo(null);
        form.setVisible(true);
        form.setTitle("My Text Editor(MTE)");
        text.getDocument().addUndoableEditListener(undoManager);

        JMenuItem cut = form.getCutItem();
        cut.setEnabled(false);
        JMenuItem copy = form.getCopyItem();
        copy.setEnabled(false);
        JMenuItem paste = form.getPasteItem();
        JMenuItem undo = form.getUndoItem();
        undo.setEnabled(false);
        JMenuItem redo = form.getRedoItem();
        redo.setEnabled(false);
        JMenuItem find = form.getFindItem();
        find.setEnabled(false);
        JMenuItem replace = form.getReplaceItem();
        JMenuItem font = form.getChaneFontItem();

        text.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                //check if document is empty
                if (!text.getText().isEmpty()) {
                    find.setEnabled(true);
                }
                //check if any text is selected
                if (text.getSelectedText() != null) {
                    cut.setEnabled(true);
                    copy.setEnabled(true);
                } else {
                    cut.setEnabled(false);
                    copy.setEnabled(false);
                }
                //check if can undo or not
                if (undoManager.canUndo()) {
                    undo.setEnabled(true);
                } else {
                    undo.setEnabled(false);
                }
                //check if can redo or not
                if (undoManager.canRedo()) {
                    redo.setEnabled(true);
                } else {
                    redo.setEnabled(false);
                }
            }
        });
        text.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                saved = false;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                saved = false;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                saved = false;
            }
        });

        form.getNewItem().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //check if user want to save the document
                if (!askSave()) {
                    return;
                }
                setSaveTitle("*untilted");
                text.setText("");
                file = null;
            }
        });
        form.getOpenItem().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //check if user want to save
                if (!askSave()) {
                    return;
                }
                //loop until user choose valid file
                while (true) {
                    int result = chooser.showOpenDialog(form);
                    //check if user chooser approve option
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File openFile = chooser.getSelectedFile();
                        //check if open file is exist
                        if (openFile.exists()) {
                            file = openFile;
                            text.setText("");
                            setSaveTitle(openFile.getName());
                            writeToTextArea(openFile);
                            saved = true;
                            break;
                        } else {
                            JOptionPane.showMessageDialog(form, "File not found", "Open", JOptionPane.OK_OPTION);
                        }
                    } else {
                        break;
                    }

                }
            }
        });
        form.getSaveItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
                saved = true;
            }
        });
        form.getSaveAsItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAs();
            }
        });
        form.getExitItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //check if user want to save
                if (!askSave()) {
                    return;
                }
                System.exit(0);
            }
        });
        form.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        form.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //check if user want to save
                if (!askSave()) {
                    return;
                }
                System.exit(0);
            }
        });

        form.getSAllItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                text.selectAll();
            }
        });

        cut.addActionListener(new DefaultEditorKit.CutAction());
        copy.addActionListener(new DefaultEditorKit.CopyAction());
        paste.addActionListener(new DefaultEditorKit.PasteAction());
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undoManager.undo();
            }
        });

        redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undoManager.redo();
            }
        });

        find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FindController findControl = new FindController(form);
            }
        });

        replace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReplaceController repControl = new ReplaceController(form);
            }
        });

        font.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeFontController font = new ChangeFontController(form);
            }
        });

    }

    private void writeToTextArea(File f) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(f));
            String line = "";
            //loop until line is null
            while (line != null) {
                try {
                    line = in.readLine();
                    //check if line is null
                    if (line == null) {
                        break;
                    }
                    text.append(line + "\n");
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
            text.setCaretPosition(0);
            saved = true;
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }

    private boolean isNeedSaving() {
        String doc = text.getText();
        //check if document is empty or not
        if (doc.isEmpty()) {
            //check if file is null or not
            if (file == null) {
                return false;
            } else {
                return !saved;
            }
        } else {
            //check if file is null or not
            if (file == null) {
                return true;
            } else {
                return !saved;
            }
        }
    }

    private boolean askSave() {
        //check if document need to save
        if (isNeedSaving()) {
            int option = JOptionPane.showConfirmDialog(form,
                    "Do you want to save the change?", "Save", JOptionPane.YES_NO_CANCEL_OPTION);
            //direct code base on user choice
            switch (option) {
                case JOptionPane.YES_OPTION:
                    //check if file is null
                    if (file == null) {
                        //check if user cancel save option
                        if (!saveAs()) {
                            return false;
                        }
                    } else {
                        //check if user cancel save option
                        if (!save()) {
                            return false;
                        }
                    }
                    break;
                case JOptionPane.CANCEL_OPTION:
                    return false;
            }
        }
        return true;
    }

    private void setSaveTitle(String title) {
        form.setTitle("My Text Editor(MTE  " + title);
    }

    private boolean saveAs() {
        File newFile;
        //loop until user choose valid option 
        while (true) {
            int result = chooser.showSaveDialog(form);
            newFile = chooser.getSelectedFile();
            //check if user choose cancel
            if (result == JFileChooser.CANCEL_OPTION) {
                return false;
            }
            //check if chosen file is exist
            if (!newFile.exists()) {
                break;
            }
            int option = JOptionPane.showConfirmDialog(form, newFile.getName() + " is already exist!\nDo you"
                    + "want to replace it?", "Save as", JOptionPane.YES_NO_OPTION);
            //check if user choose yes
            if (option == JOptionPane.YES_OPTION) {
                break;
            }
        }
        file = newFile;
        save();
        setSaveTitle(newFile.getName());
        saved = true;
        return true;
    }

    private boolean save() {
        FileWriter fw = null;
        //check if file is null
        if (file == null) {
            return saveAs();
        }
        try {
            fw = new FileWriter(file);
            fw.write(text.getText());
            saved = true;
        } catch (IOException ex) {
            System.out.println(ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
        return true;
    }

}
