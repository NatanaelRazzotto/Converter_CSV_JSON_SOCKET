package com.razzotto.Entidade;

public class Pessoa {
	public Pessoa(String codigo, String gender, String nameSet, String title, String givenName, String surname,
			String streetAddress, String city, String state, String zipCode, String countryFull, String emailAddress,
			String username, String password, String telephoneNumber, String birthday, String cCType, String cCNumber,
			String cVV2, String cCExpires, String nationalID, String color, String kilograms, String centimeters,
			String gUID) {
		super();
		Codigo = codigo;
		Gender = gender;
		NameSet = nameSet;
		Title = title;
		GivenName = givenName;
		Surname = surname;
		StreetAddress = streetAddress;
		City = city;
		State = state;
		ZipCode = zipCode;
		CountryFull = countryFull;
		EmailAddress = emailAddress;
		Username = username;
		Password = password;
		TelephoneNumber = telephoneNumber;
		Birthday = birthday;
		CCType = cCType;
		CCNumber = cCNumber;
		CVV2 = cVV2;
		CCExpires = cCExpires;
		NationalID = nationalID;
		Color = color;
		Kilograms = kilograms;
		Centimeters = centimeters;
		GUID = gUID;
	}
	String Codigo;
	String Gender;
	String NameSet;
	String Title;
	String GivenName;
	String Surname;
	String StreetAddress;
	String City;
	String State;
	String ZipCode;
	String CountryFull;
	String EmailAddress;
	String Username;
	String Password;
	String TelephoneNumber;
	String Birthday;//////
	String CCType;
	String CCNumber;
	String CVV2;
	String CCExpires;
	String NationalID;
	String Color;
	String Kilograms;
	String Centimeters;
	String GUID;
//25
public String getCodigo() {
	return Codigo;
}
public void setCodigo(String codigo) {
	Codigo = codigo;
}
public String getGender() {
	return Gender;
}
public void setGender(String gender) {
	Gender = gender;
}
public String getNameSet() {
	return NameSet;
}
public void setNameSet(String nameSet) {
	NameSet = nameSet;
}
public String getTitle() {
	return Title;
}
public void setTitle(String title) {
	Title = title;
}
public String getGivenName() {
	return GivenName;
}
public void setGivenName(String givenName) {
	GivenName = givenName;
}
public String getSurname() {
	return Surname;
}
public void setSurname(String surname) {
	Surname = surname;
}
public String getStreetAddress() {
	return StreetAddress;
}
public void setStreetAddress(String streetAddress) {
	StreetAddress = streetAddress;
}
public String getCity() {
	return City;
}
public void setCity(String city) {
	City = city;
}
public String getState() {
	return State;
}
public void setState(String state) {
	State = state;
}
public String getZipCode() {
	return ZipCode;
}
public void setZipCode(String zipCode) {
	ZipCode = zipCode;
}
public String getCountryFull() {
	return CountryFull;
}
public void setCountryFull(String countryFull) {
	CountryFull = countryFull;
}
public String getEmailAddress() {
	return EmailAddress;
}
public void setEmailAddress(String emailAddress) {
	EmailAddress = emailAddress;
}
public String getUsername() {
	return Username;
}
public void setUsername(String username) {
	Username = username;
}
public String getPassword() {
	return Password;
}
public void setPassword(String password) {
	Password = password;
}
public String getTelephoneNumber() {
	return TelephoneNumber;
}
public void setTelephoneNumber(String telephoneNumber) {
	TelephoneNumber = telephoneNumber;
}
public String getBirthday() {
	return Birthday;
}
public void setBirthday(String birthday) {
	Birthday = birthday;
}
public String getCCType() {
	return CCType;
}
public void setCCType(String cCType) {
	CCType = cCType;
}
public String getCCNumber() {
	return CCNumber;
}
public void setCCNumber(String cCNumber) {
	CCNumber = cCNumber;
}
public String getCVV2() {
	return CVV2;
}
public void setCVV2(String cVV2) {
	CVV2 = cVV2;
}
public String getCCExpires() {
	return CCExpires;
}
public void setCCExpires(String cCExpires) {
	CCExpires = cCExpires;
}
public String getNationalID() {
	return NationalID;
}
public void setNationalID(String nationalID) {
	NationalID = nationalID;
}
public String getColor() {
	return Color;
}
public void setColor(String color) {
	Color = color;
}
public String getKilograms() {
	return Kilograms;
}
public void setKilograms(String kilograms) {
	Kilograms = kilograms;
}
public String getCentimeters() {
	return Centimeters;
}
public void setCentimeters(String centimeters) {
	Centimeters = centimeters;
}
public String getGUID() {
	return GUID;
}
public void setGUID(String gUID) {
	GUID = gUID;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((Birthday == null) ? 0 : Birthday.hashCode());
	result = prime * result + ((CCExpires == null) ? 0 : CCExpires.hashCode());
	result = prime * result + ((CCNumber == null) ? 0 : CCNumber.hashCode());
	result = prime * result + ((CCType == null) ? 0 : CCType.hashCode());
	result = prime * result + ((CVV2 == null) ? 0 : CVV2.hashCode());
	result = prime * result + ((Centimeters == null) ? 0 : Centimeters.hashCode());
	result = prime * result + ((City == null) ? 0 : City.hashCode());
	result = prime * result + ((Codigo == null) ? 0 : Codigo.hashCode());
	result = prime * result + ((Color == null) ? 0 : Color.hashCode());
	result = prime * result + ((CountryFull == null) ? 0 : CountryFull.hashCode());
	result = prime * result + ((EmailAddress == null) ? 0 : EmailAddress.hashCode());
	result = prime * result + ((GUID == null) ? 0 : GUID.hashCode());
	result = prime * result + ((Gender == null) ? 0 : Gender.hashCode());
	result = prime * result + ((GivenName == null) ? 0 : GivenName.hashCode());
	result = prime * result + ((Kilograms == null) ? 0 : Kilograms.hashCode());
	result = prime * result + ((NameSet == null) ? 0 : NameSet.hashCode());
	result = prime * result + ((NationalID == null) ? 0 : NationalID.hashCode());
	result = prime * result + ((Password == null) ? 0 : Password.hashCode());
	result = prime * result + ((State == null) ? 0 : State.hashCode());
	result = prime * result + ((StreetAddress == null) ? 0 : StreetAddress.hashCode());
	result = prime * result + ((Surname == null) ? 0 : Surname.hashCode());
	result = prime * result + ((TelephoneNumber == null) ? 0 : TelephoneNumber.hashCode());
	result = prime * result + ((Title == null) ? 0 : Title.hashCode());
	result = prime * result + ((Username == null) ? 0 : Username.hashCode());
	result = prime * result + ((ZipCode == null) ? 0 : ZipCode.hashCode());
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Pessoa other = (Pessoa) obj;
	if (Birthday == null) {
		if (other.Birthday != null)
			return false;
	} else if (!Birthday.equals(other.Birthday))
		return false;
	if (CCExpires == null) {
		if (other.CCExpires != null)
			return false;
	} else if (!CCExpires.equals(other.CCExpires))
		return false;
	if (CCNumber == null) {
		if (other.CCNumber != null)
			return false;
	} else if (!CCNumber.equals(other.CCNumber))
		return false;
	if (CCType == null) {
		if (other.CCType != null)
			return false;
	} else if (!CCType.equals(other.CCType))
		return false;
	if (CVV2 == null) {
		if (other.CVV2 != null)
			return false;
	} else if (!CVV2.equals(other.CVV2))
		return false;
	if (Centimeters == null) {
		if (other.Centimeters != null)
			return false;
	} else if (!Centimeters.equals(other.Centimeters))
		return false;
	if (City == null) {
		if (other.City != null)
			return false;
	} else if (!City.equals(other.City))
		return false;
	if (Codigo == null) {
		if (other.Codigo != null)
			return false;
	} else if (!Codigo.equals(other.Codigo))
		return false;
	if (Color == null) {
		if (other.Color != null)
			return false;
	} else if (!Color.equals(other.Color))
		return false;
	if (CountryFull == null) {
		if (other.CountryFull != null)
			return false;
	} else if (!CountryFull.equals(other.CountryFull))
		return false;
	if (EmailAddress == null) {
		if (other.EmailAddress != null)
			return false;
	} else if (!EmailAddress.equals(other.EmailAddress))
		return false;
	if (GUID == null) {
		if (other.GUID != null)
			return false;
	} else if (!GUID.equals(other.GUID))
		return false;
	if (Gender == null) {
		if (other.Gender != null)
			return false;
	} else if (!Gender.equals(other.Gender))
		return false;
	if (GivenName == null) {
		if (other.GivenName != null)
			return false;
	} else if (!GivenName.equals(other.GivenName))
		return false;
	if (Kilograms == null) {
		if (other.Kilograms != null)
			return false;
	} else if (!Kilograms.equals(other.Kilograms))
		return false;
	if (NameSet == null) {
		if (other.NameSet != null)
			return false;
	} else if (!NameSet.equals(other.NameSet))
		return false;
	if (NationalID == null) {
		if (other.NationalID != null)
			return false;
	} else if (!NationalID.equals(other.NationalID))
		return false;
	if (Password == null) {
		if (other.Password != null)
			return false;
	} else if (!Password.equals(other.Password))
		return false;
	if (State == null) {
		if (other.State != null)
			return false;
	} else if (!State.equals(other.State))
		return false;
	if (StreetAddress == null) {
		if (other.StreetAddress != null)
			return false;
	} else if (!StreetAddress.equals(other.StreetAddress))
		return false;
	if (Surname == null) {
		if (other.Surname != null)
			return false;
	} else if (!Surname.equals(other.Surname))
		return false;
	if (TelephoneNumber == null) {
		if (other.TelephoneNumber != null)
			return false;
	} else if (!TelephoneNumber.equals(other.TelephoneNumber))
		return false;
	if (Title == null) {
		if (other.Title != null)
			return false;
	} else if (!Title.equals(other.Title))
		return false;
	if (Username == null) {
		if (other.Username != null)
			return false;
	} else if (!Username.equals(other.Username))
		return false;
	if (ZipCode == null) {
		if (other.ZipCode != null)
			return false;
	} else if (!ZipCode.equals(other.ZipCode))
		return false;
	return true;
}
@Override
public String toString() {
	return "Pessoa [Codigo=" + Codigo + ", Gender=" + Gender + ", NameSet=" + NameSet + ", Title=" + Title
			+ ", GivenName=" + GivenName + ", Surname=" + Surname + ", StreetAddress=" + StreetAddress + ", City="
			+ City + ", State=" + State + ", ZipCode=" + ZipCode + ", CountryFull=" + CountryFull + ", EmailAddress="
			+ EmailAddress + ", Username=" + Username + ", Password=" + Password + ", TelephoneNumber="
			+ TelephoneNumber + ", Birthday=" + Birthday + ", CCType=" + CCType + ", CCNumber=" + CCNumber + ", CVV2="
			+ CVV2 + ", CCExpires=" + CCExpires + ", NationalID=" + NationalID + ", Color=" + Color + ", Kilograms="
			+ Kilograms + ", Centimeters=" + Centimeters + ", GUID=" + GUID + "]";
}



}



