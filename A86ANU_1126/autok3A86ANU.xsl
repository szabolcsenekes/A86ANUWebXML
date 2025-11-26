<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/">
        <html>
        <head>
            <meta charset="UTF-8"/>
            <title>Elemek száma</title>
        </head>
        <body>
            <h2>Az XML dokumentum elemeinek száma</h2>
            <p>
                Összes elem (//*): 
                <b><xsl:value-of select="count(//*)"/></b>
            </p>
        </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
