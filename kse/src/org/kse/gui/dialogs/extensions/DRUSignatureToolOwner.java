/*
 * Copyright 2004 - 2013 Wayne Grant
 *           2013 - 2020 Kai Kramer
 *
 * This file is part of KeyStore Explorer.
 *
 * KeyStore Explorer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * KeyStore Explorer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with KeyStore Explorer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.kse.gui.dialogs.extensions;

import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERUTF8String;
import org.kse.gui.LnfUtil;
import org.kse.gui.PlatformUtil;
import org.kse.gui.error.DError;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Dialog used to add or edit a Netscape Comment extension.
 */
public class DRUSignatureToolOwner extends DExtension {
    private static final long serialVersionUID = 1L;

    private static ResourceBundle res = ResourceBundle
            .getBundle("org/kse/gui/dialogs/extensions/resources");

    private static final String CANCEL_KEY = "CANCEL_KEY";

    private JTextField jtfRUSignatureToolOwner;

    private byte[] value;

    /**
     * Creates a new DRUSignatureToolOwner dialog.
     *
     * @param parent The parent dialog
     */
    public DRUSignatureToolOwner(JDialog parent) {
        super(parent);
        setTitle(res.getString("DRUSignatureToolOwner.Title"));
        initComponents();
    }

    /**
     * Creates a new DRUSignatureToolOwner dialog.
     *
     * @param parent The parent dialog
     * @param value  Netscape Base URL DER-encoded
     * @throws IOException If value could not be decoded
     */
    public DRUSignatureToolOwner(JDialog parent, byte[] value) throws IOException {
        super(parent);
        setTitle(res.getString("DRUSignatureToolOwner.Title"));
        initComponents();
        prepopulateWithValue(value);
    }

    private void initComponents() {
        JLabel jlRUSignatureToolOwner = new JLabel(res.getString("DRUSignatureToolOwner.jlRUSignatureToolOwner.text"));

        jtfRUSignatureToolOwner = new JTextField(40);

        JPanel jpRUSignatureToolOwner = new JPanel(new BorderLayout(5, 5));

        jpRUSignatureToolOwner.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), new CompoundBorder(
                new EtchedBorder(), new EmptyBorder(5, 5, 5, 5))));

        jpRUSignatureToolOwner.add(jlRUSignatureToolOwner, BorderLayout.NORTH);
        jpRUSignatureToolOwner.add(jtfRUSignatureToolOwner, BorderLayout.CENTER);

        JButton jbOK = new JButton(res.getString("DRUSignatureToolOwner.jbOK.text"));
        jbOK.addActionListener(evt -> okPressed());

        JButton jbCancel = new JButton(res.getString("DRUSignatureToolOwner.jbCancel.text"));
        jbCancel.addActionListener(evt -> cancelPressed());
        jbCancel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                CANCEL_KEY);
        jbCancel.getActionMap().put(CANCEL_KEY, new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent evt) {
                cancelPressed();
            }
        });

        JPanel jpButtons = PlatformUtil.createDialogButtonPanel(jbOK, jbCancel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jpRUSignatureToolOwner, BorderLayout.CENTER);
        getContentPane().add(jpButtons, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                closeDialog();
            }
        });

        setResizable(false);

        getRootPane().setDefaultButton(jbOK);

        pack();
    }

    private void prepopulateWithValue(byte[] value) throws IOException {
        DERUTF8String RUSignatureToolOwner = DERUTF8String.getInstance(value);

        jtfRUSignatureToolOwner.setText(RUSignatureToolOwner.getString());
        jtfRUSignatureToolOwner.setCaretPosition(0);
    }

    private void okPressed() {
        String RUSignatureToolOwnerStr = jtfRUSignatureToolOwner.getText().trim();

        if (RUSignatureToolOwnerStr.length() == 0) {
            JOptionPane.showMessageDialog(this, res.getString("DRUSignatureToolOwner.ValueReq.message"), getTitle(),
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        DERUTF8String value = new DERUTF8String(RUSignatureToolOwnerStr);

        try {
            this.value = value.getEncoded(ASN1Encoding.DER);
        } catch (IOException e) {
            DError.displayError(this, e);
            return;
        }

        closeDialog();
    }

    /**
     * Get extension value DER-encoded.
     *
     * @return Extension value
     */
    @Override
    public byte[] getValue() {
        return value;
    }

    private void cancelPressed() {
        closeDialog();
    }

    private void closeDialog() {
        setVisible(false);
        dispose();
    }
}
