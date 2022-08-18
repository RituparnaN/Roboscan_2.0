//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.04.27 at 05:46:50 PM IST 
//


package com.quantumdataengines.app.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for authentication complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="authentication">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="ldapProvider" type="{}ldapProvider" minOccurs="0"/>
 *         &lt;element name="ssoProvider" type="{}ssoProvider" minOccurs="0"/>
 *         &lt;element name="webServiceProvider" type="{}webServiceProvider" minOccurs="0"/>
 *       &lt;/all>
 *       &lt;attribute name="provider" use="required" type="{}provider" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "authentication", propOrder = {

})
public class Authentication {

    protected LdapProvider ldapProvider;
    protected SsoProvider ssoProvider;
    protected WebServiceProvider webServiceProvider;
    @XmlAttribute(name = "provider", required = true)
    protected Provider provider;

    /**
     * Gets the value of the ldapProvider property.
     * 
     * @return
     *     possible object is
     *     {@link LdapProvider }
     *     
     */
    public LdapProvider getLdapProvider() {
        return ldapProvider;
    }

    /**
     * Sets the value of the ldapProvider property.
     * 
     * @param value
     *     allowed object is
     *     {@link LdapProvider }
     *     
     */
    public void setLdapProvider(LdapProvider value) {
        this.ldapProvider = value;
    }

    /**
     * Gets the value of the ssoProvider property.
     * 
     * @return
     *     possible object is
     *     {@link SsoProvider }
     *     
     */
    public SsoProvider getSsoProvider() {
        return ssoProvider;
    }

    /**
     * Sets the value of the ssoProvider property.
     * 
     * @param value
     *     allowed object is
     *     {@link SsoProvider }
     *     
     */
    public void setSsoProvider(SsoProvider value) {
        this.ssoProvider = value;
    }

    /**
     * Gets the value of the webServiceProvider property.
     * 
     * @return
     *     possible object is
     *     {@link WebServiceProvider }
     *     
     */
    public WebServiceProvider getWebServiceProvider() {
        return webServiceProvider;
    }

    /**
     * Sets the value of the webServiceProvider property.
     * 
     * @param value
     *     allowed object is
     *     {@link WebServiceProvider }
     *     
     */
    public void setWebServiceProvider(WebServiceProvider value) {
        this.webServiceProvider = value;
    }

    /**
     * Gets the value of the provider property.
     * 
     * @return
     *     possible object is
     *     {@link Provider }
     *     
     */
    public Provider getProvider() {
        return provider;
    }

    /**
     * Sets the value of the provider property.
     * 
     * @param value
     *     allowed object is
     *     {@link Provider }
     *     
     */
    public void setProvider(Provider value) {
        this.provider = value;
    }

}
