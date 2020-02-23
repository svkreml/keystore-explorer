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
package org.kse.gui.dnchooser;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.kse.crypto.x509.KseX500NameStyle;

/**
 * This class holds the mapping between the OIDs and the display names of RDN components.
 */
public class OidDisplayNameMapping {

    private static ResourceBundle res = ResourceBundle.getBundle("org/kse/gui/dnchooser/resources");

    private static final String DC = res.getString("DistinguishedNameChooser.jlDomainComponent.text");
    private static final String SURNAME = res.getString("DistinguishedNameChooser.jlSurname.text");
    private static final String GIVENNAME = res.getString("DistinguishedNameChooser.jlGivenName.text");
    private static final String SN = res.getString("DistinguishedNameChooser.jlSerialNumber.text");
    private static final String E = res.getString("DistinguishedNameChooser.jlEmailAddress.text");
    private static final String C = res.getString("DistinguishedNameChooser.jlCountryCode.text");
    private static final String ST = res.getString("DistinguishedNameChooser.jlStateName.text");
    private static final String L = res.getString("DistinguishedNameChooser.jlLocalityName.text");
    private static final String O = res.getString("DistinguishedNameChooser.jlOrganisationName.text");
    private static final String OU = res.getString("DistinguishedNameChooser.jlOrganisationUnit.text");
    private static final String CN = res.getString("DistinguishedNameChooser.jlCommonName.text");
    private static final String UID = res.getString("DistinguishedNameChooser.jlUserID.text");

    private static final String INN = res.getString("DistinguishedNameChooser.jlINN.text");
    private static final String OGRN = res.getString("DistinguishedNameChooser.jlOGRN.text");
    private static final String OGRNIP = res.getString("DistinguishedNameChooser.jlOGRNIP.text");
    private static final String SNILS = res.getString("DistinguishedNameChooser.jlSNILS.text");
    private static final String STREET = res.getString("DistinguishedNameChooser.jlSTREET.text");


    private static Map<String, ASN1ObjectIdentifier> displayNameToOID = new HashMap<>();

    static {
        displayNameToOID.put(CN, BCStyle.CN);
        displayNameToOID.put(OU, BCStyle.OU);
        displayNameToOID.put(O, BCStyle.O);
        displayNameToOID.put(L, BCStyle.L);
        displayNameToOID.put(ST, BCStyle.ST);
        displayNameToOID.put(C, BCStyle.C);
        displayNameToOID.put(E, BCStyle.E);

        displayNameToOID.put(SN, BCStyle.SN);
        displayNameToOID.put(GIVENNAME, BCStyle.GIVENNAME);
        displayNameToOID.put(SURNAME, BCStyle.SURNAME);
        displayNameToOID.put(DC, BCStyle.DC);
        displayNameToOID.put(UID, BCStyle.UID);

        displayNameToOID.put(INN, KseX500NameStyle.INN);
        displayNameToOID.put(OGRN, KseX500NameStyle.OGRN);
        displayNameToOID.put(OGRNIP, KseX500NameStyle.OGRNIP);
        displayNameToOID.put(SNILS, KseX500NameStyle.SNILS);
        displayNameToOID.put(STREET, KseX500NameStyle.STREET);
    }

    private static Map<String, String> oidToDisplayName = new HashMap<>();

    static {
        oidToDisplayName.put(BCStyle.CN.getId(), CN);
        oidToDisplayName.put(BCStyle.OU.getId(), OU);
        oidToDisplayName.put(BCStyle.O.getId(), O);
        oidToDisplayName.put(BCStyle.L.getId(), L);
        oidToDisplayName.put(BCStyle.ST.getId(), ST);
        oidToDisplayName.put(BCStyle.C.getId(), C);
        oidToDisplayName.put(BCStyle.E.getId(), E);

        oidToDisplayName.put(BCStyle.SN.getId(), SN);
        oidToDisplayName.put(BCStyle.GIVENNAME.getId(), GIVENNAME);
        oidToDisplayName.put(BCStyle.SURNAME.getId(), SURNAME);
        oidToDisplayName.put(BCStyle.DC.getId(), DC);
        oidToDisplayName.put(BCStyle.UID.getId(), UID);


        oidToDisplayName.put(KseX500NameStyle.INN.getId(), INN);
        oidToDisplayName.put(KseX500NameStyle.OGRN.getId(), OGRN);
        oidToDisplayName.put(KseX500NameStyle.OGRNIP.getId(), OGRNIP);
        oidToDisplayName.put(KseX500NameStyle.SNILS.getId(), SNILS);
        oidToDisplayName.put(KseX500NameStyle.STREET.getId(), STREET);
    }

    public static String[] getDisplayNames() {
        return new String[]{CN, OU, O, L, ST, C, E, SN, GIVENNAME, SURNAME, DC, UID, INN, OGRN, OGRNIP, SNILS, STREET};
    }

    public static ASN1ObjectIdentifier getOidForDisplayName(String displayName) {
        return displayNameToOID.get(displayName);
    }

    public static String getDisplayNameForOid(String oid) {
        String displayName = oidToDisplayName.get(oid);
        if (displayName == null) {
            // simply use OID as display name for unknown OIDs
            displayName = oid;
        }
        return displayName;
    }

    private OidDisplayNameMapping() {
    }
}
