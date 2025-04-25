<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="html" indent="yes"/>

    <xsl:template match="/tickets">
        <html>
            <head>
                <title>Tickets Export</title>
                <style>
                    table { border-collapse: collapse; width: 100%; }
                    th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
                    th { background-color: #f0f0f0; }
                </style>
            </head>
            <body>
                <h2>Tickets</h2>
                <table>
                    <tr>
                        <th>ID</th>
                        <th>Seat</th>
                        <th>Price</th>
                        <th>Event</th>
                        <th>Date</th>
                        <th>Venue</th>
                        <th>User</th>
                    </tr>
                    <xsl:for-each select="ticket">
                        <tr>
                            <td><xsl:value-of select="id"/></td>
                            <td><xsl:value-of select="seatNumber"/></td>
                            <td><xsl:value-of select="price"/></td>
                            <td><xsl:value-of select="event/name"/></td>
                            <td><xsl:value-of select="event/date"/></td>
                            <td><xsl:value-of select="event/venue/name"/></td>
                            <td><xsl:value-of select="user/name"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
