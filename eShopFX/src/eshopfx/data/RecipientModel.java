/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eshopfx.data;

/**
 *
 * @author Parth
 */
public class RecipientModel
{
    private int recipientID;
    private String name;
    private String streetAddr;
    private String zip;
    private String country;
    private String state;
    private String city;

    public RecipientModel()
    {
    }

    public int getRecipientID()
    {
        return recipientID;
    }

    public void setRecipientID(int recipientID)
    {
        this.recipientID = recipientID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getStreetAddr()
    {
        return streetAddr;
    }

    public void setStreetAddr(String streetAddr)
    {
        this.streetAddr = streetAddr;
    }

    public String getZip()
    {
        return zip;
    }

    public void setZip(String zip)
    {
        this.zip = zip;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }
    
    
}
