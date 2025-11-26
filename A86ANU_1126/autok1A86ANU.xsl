<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/autok">
        <html>
        <head>
            <meta charset="UTF-8"/>
            <title>Autók rendszáma és ára</title>
        </head>
        <body>
            <h2>Autók rendszáma és ára (ár szerint rendezve)</h2>
            <table border="1" cellpadding="4" cellspacing="0">
                <tr>
                    <th>Rendszám</th>
                    <th>Ár</th>
                </tr>
                <xsl:for-each select="auto">
                    <xsl:sort select="ar" data-type="number" order="ascending"/>
                    <tr>
                        <td><xsl:value-of select="@rsz"/></td>
                        <td><xsl:value-of select="ar"/></td>
                    </tr>
                </xsl:for-each>
            </table>
        </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
