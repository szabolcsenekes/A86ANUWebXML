<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <!-- kulcs típus szerint -->
    <xsl:key name="kTipus" match="auto" use="tipus"/>

    <xsl:template match="/autok">
        <html>
        <head>
            <meta charset="UTF-8"/>
            <title>Autótípusok darabszáma</title>
        </head>
        <body>
            <h2>Autótípusok darabszáma (csökkenő sorrendben)</h2>
            <table border="1" cellpadding="4" cellspacing="0">
                <tr>
                    <th>Típus</th>
                    <th>Darab</th>
                </tr>

                <!-- csak az első auto adott típusra -->
                <xsl:for-each select="auto[generate-id()=generate-id(key('kTipus', tipus)[1])]">
                    <!-- rendezés darabszám szerint -->
                    <xsl:sort select="count(key('kTipus', tipus))"
                              data-type="number" order="descending"/>
                    <tr>
                        <td><xsl:value-of select="tipus"/></td>
                        <td><xsl:value-of select="count(key('kTipus', tipus))"/></td>
                    </tr>
                </xsl:for-each>
            </table>
        </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
