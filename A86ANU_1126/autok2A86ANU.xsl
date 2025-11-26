<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/autok">
        <html>
        <head>
            <meta charset="UTF-8"/>
            <title>30000-nél drágább autók</title>
        </head>
        <body>
            <h2>30000-nél drágább autók száma</h2>
            <p>
                Darabszám:
                <b><xsl:value-of select="count(auto[ar &gt; 30000])"/></b>
            </p>
        </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
