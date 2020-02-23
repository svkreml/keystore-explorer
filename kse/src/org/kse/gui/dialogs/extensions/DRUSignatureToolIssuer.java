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

import net.miginfocom.swing.MigLayout;
import org.bouncycastle.asn1.*;
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
public class DRUSignatureToolIssuer extends DExtension {
    private static final long serialVersionUID = 1L;

    private static ResourceBundle res = ResourceBundle
            .getBundle("org/kse/gui/dialogs/extensions/resources");

    private static final String CANCEL_KEY = "CANCEL_KEY";

    private JTextField jtfNetscapeBaseUrl1;
    private JTextField jtfNetscapeBaseUrl2;
    private JTextField jtfNetscapeBaseUrl3;
    private JTextField jtfNetscapeBaseUrl4;

    private byte[] value;

    /**
     * Creates a new DRUSignatureToolIssuer dialog.
     *
     * @param parent The parent dialog
     */
    public DRUSignatureToolIssuer(JDialog parent) {
        super(parent);
        setTitle(res.getString("DRUSignatureToolIssuer.Title"));
        initComponents();
    }

    /**
     * Creates a new DRUSignatureToolIssuer dialog.
     *
     * @param parent The parent dialog
     * @param value  Netscape Base URL DER-encoded
     * @throws IOException If value could not be decoded
     */
    public DRUSignatureToolIssuer(JDialog parent, byte[] value) throws IOException {
        super(parent);
        setTitle(res.getString("DRUSignatureToolIssuer.Title"));
        initComponents();
        prepopulateWithValue(value);
    }

    private void initComponents() {
        JLabel jlNetscapeBaseUrl = new JLabel(res.getString("DRUSignatureToolIssuer.jlNetscapeBaseUrl.text"));

        jtfNetscapeBaseUrl1 = new JTextField(40);
        jtfNetscapeBaseUrl2 = new JTextField(40);
        jtfNetscapeBaseUrl3 = new JTextField(40);
        jtfNetscapeBaseUrl4 = new JTextField(40);

        JPanel jpNetscapeBaseUrl = new JPanel(new BorderLayout(5, 5));

        jpNetscapeBaseUrl.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), new CompoundBorder(
                new EtchedBorder(), new EmptyBorder(5, 5, 5, 5))));

        jpNetscapeBaseUrl.add(jlNetscapeBaseUrl, BorderLayout.NORTH);

        jpNetscapeBaseUrl.add(
				new JPanel(new MigLayout()){{
					add(jtfNetscapeBaseUrl1,"wrap");
					add(jtfNetscapeBaseUrl2,"wrap");
					add(jtfNetscapeBaseUrl3,"wrap");
					add(jtfNetscapeBaseUrl4,"wrap");
				}}
		);

        JButton jbOK = new JButton(res.getString("DRUSignatureToolIssuer.jbOK.text"));
        jbOK.addActionListener(evt -> okPressed());

        JButton jbCancel = new JButton(res.getString("DRUSignatureToolIssuer.jbCancel.text"));
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
        getContentPane().add(jpNetscapeBaseUrl, BorderLayout.CENTER);
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
        ASN1Encodable[] lines = DERSequence.getInstance(value).toArray();
        jtfNetscapeBaseUrl1.setText(DERUTF8String.getInstance(lines[0]).getString());
        jtfNetscapeBaseUrl1.setCaretPosition(0);
        jtfNetscapeBaseUrl2.setText(DERUTF8String.getInstance(lines[0]).getString());
        jtfNetscapeBaseUrl2.setCaretPosition(0);
        jtfNetscapeBaseUrl3.setText(DERUTF8String.getInstance(lines[0]).getString());
        jtfNetscapeBaseUrl3.setCaretPosition(0);
        jtfNetscapeBaseUrl4.setText(DERUTF8String.getInstance(lines[0]).getString());
        jtfNetscapeBaseUrl4.setCaretPosition(0);
    }

    private void okPressed() {
        String netscapeBaseUrlStr1 = jtfNetscapeBaseUrl1.getText().trim();
        String netscapeBaseUrlStr2 = jtfNetscapeBaseUrl2.getText().trim();
        String netscapeBaseUrlStr3 = jtfNetscapeBaseUrl3.getText().trim();
        String netscapeBaseUrlStr4 = jtfNetscapeBaseUrl4.getText().trim();

        if (netscapeBaseUrlStr1.length() == 0
                || netscapeBaseUrlStr2.length() == 0
                || netscapeBaseUrlStr3.length() == 0
                || netscapeBaseUrlStr4.length() == 0) {
            JOptionPane.showMessageDialog(this, res.getString("DRUSignatureToolIssuer.ValueReq.message"), getTitle(),
                    JOptionPane.WARNING_MESSAGE);
            return;
        }


        DERSequence derSequence = new DERSequence(new ASN1EncodableVector() {{
            add(new DERUTF8String(netscapeBaseUrlStr1));
            add(new DERUTF8String(netscapeBaseUrlStr2));
            add(new DERUTF8String(netscapeBaseUrlStr3));
            add(new DERUTF8String(netscapeBaseUrlStr4));
        }}
        );

        try {
            this.value = derSequence.getEncoded(ASN1Encoding.DER);
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
