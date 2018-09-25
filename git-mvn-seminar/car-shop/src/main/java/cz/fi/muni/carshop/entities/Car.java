package cz.fi.muni.carshop.entities;

import java.awt.Color;

import cz.fi.muni.carshop.enums.CarTypes;
import java.util.Objects;

public class Car {

	private Color color;
	private CarTypes type;
	private int constructionYear;
	private int price;

	public Car(Color color, CarTypes type, int constructionYear, int price) {
		super();
		this.color = color;
		this.type = type;
		this.constructionYear = constructionYear;
		this.price = price;
	}

	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public CarTypes getType() {
		return type;
	}
	
	public int getConstructionYear() {
		return constructionYear;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

        @Override
        public String toString() {
                return "Car{" + "color=" + color + ", type=" + type + ", constructionYear=" + constructionYear + ", price=" + price + '}';
        }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Car other = (Car) obj;
        if (this.constructionYear != other.constructionYear) {
            return false;
        }
        if (this.price != other.price) {
            return false;
        }
        if (!Objects.equals(this.color, other.color)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.color);
        hash = 43 * hash + Objects.hashCode(this.type);
        hash = 43 * hash + this.constructionYear;
        hash = 43 * hash + this.price;
        return hash;
    }
        
        
	
}
