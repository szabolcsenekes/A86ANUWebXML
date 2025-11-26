<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <!-- kulcs város szerint -->
    <xsl:key name="kVaros" match="auto" use="tulaj/varos"/>

    <xsl:template match="/autok">
        <html>
        <head>
            <meta charset="UTF-8"/>
            <title>Autók városonként</title>
        </head>
        <body>
            <h2>Autók városonként – darabszám és összár</h2>
            <table border="1" cellpadding="4" cellspacing="0">
                <tr>
                    <th>Város</th>
                    <th>Autók száma</th>
                    <th>Összár</th>
                </tr>

                <!-- egy sor városonként -->
                <xsl:for-each select="auto[generate-id()=generate-id(key('kVaros', tulaj/varos)[1])]">
                    <tr>
                        <td><xsl:value-of select="tulaj/varos"/></td>
                        <td><xsl:value-of select="count(key('kVaros', tulaj/varos))"/></td>
                        <td><xsl:value-of select="sum(key('kVaros', tulaj/varos)/ar)"/></td>
                    </tr>
                </xsl:for-each>
            </table>
        </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
