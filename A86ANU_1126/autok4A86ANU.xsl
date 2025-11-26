<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/autok">
        <html>
        <head>
            <meta charset="UTF-8"/>
            <title>Miskolci autók</title>
        </head>
        <body>
            <h2>Miskolci tulajdonosok autóinak rendszámai</h2>
            <ul>
                <xsl:for-each select="auto[tulaj/varos='Miskolc']">
                    <li><xsl:value-of select="@rsz"/></li>
                </xsl:for-each>
            </ul>
        </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
