<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" omit-xml-declaration="yes" indent="no"/>
    <xsl:template match="tearDown"/>
    <xsl:template match="testng-results">
        class,testcasename,testcasedurationforonebrowserosconfig,os,browser, browserversion, status,exception, detailedexception,testexecstart,testexecend
        <xsl:for-each select="//test-method[ @name[.!='tearDown'] and @name[.!='setUp'] and @name[.!='shutDownDriver'] ]">
            <xsl:value-of select="concat(../@name,',',@name[.!='tearDown'], ',' ,
                    @duration-ms,',', normalize-space(params/param[@index='0']),
                    ',', normalize-space(params/param[@index='2']),
                    ',', normalize-space(params/param[@index='3']),
                    ',',@status, ',', exception/@class,',',
                    normalize-space(translate(exception/full-stacktrace,',',';')),',',@started-at,',',@finished-at,',',
                    '&#xA;')"/>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>