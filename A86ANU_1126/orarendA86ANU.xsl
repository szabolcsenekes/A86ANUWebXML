<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">

    <!-- Gyökérelem: /A86ANU_orarend -->
    <xsl:template match="/A86ANU_orarend">
        <html>
        <head>
            <meta charset="UTF-8"/>
            <title>A86ANU Órarend - 2025. I. félév</title>
        </head>
        <body>
            <h2>A86ANU Órarend - 2025. I. félév</h2>

            <table border="1" cellpadding="4" cellspacing="0">
                <tr>
                    <th>ID</th>
                    <th>Típus</th>
                    <th>Tárgy</th>
                    <th>Nap</th>
                    <th>Tól</th>
                    <th>Ig</th>
                    <th>Helyszín</th>
                    <th>Oktató</th>
                    <th>Szak</th>
                </tr>

                <!-- for-each az órákra -->
                <xsl:for-each select="ora">
                    <tr>
                        <td><xsl:value-of select="@id"/></td>
                        <td><xsl:value-of select="@tipus"/></td>
                        <td><xsl:value-of select="targy"/></td>
                        <td><xsl:value-of select="idopont/nap"/></td>
                        <td><xsl:value-of select="idopont/tol"/></td>
                        <td><xsl:value-of select="idopont/ig"/></td>
                        <td><xsl:value-of select="helyszin"/></td>
                        <td><xsl:value-of select="oktato"/></td>
                        <td><xsl:value-of select="szak"/></td>
                    </tr>
                </xsl:for-each>
            </table>
        </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
