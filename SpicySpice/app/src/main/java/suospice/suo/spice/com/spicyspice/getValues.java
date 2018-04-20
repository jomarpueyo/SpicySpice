package suospice.suo.spice.com.spicyspice;

public class getValues{
    private String name, desc, image, ingred;

    public getValues(){
    }

    public getValues(String name, String desc, String image, String ingred){

        this.name = name;
        this.desc = desc;
        this.image= image;
        this.ingred= ingred;
    }

    public String getName(){
        return name;
    }
    public String getDesc(){
        return desc;
    }
    public String getImage(){
        return image;
    }
    public String getIngred(){
        return ingred;
    }

    public void setName(){
        this.name = name;
    }
    public void setDesc(){
        this.desc = desc;
    }
    public void setImage(){
        this.image = image;
    }
    public void setIngred(){
        this.ingred = ingred;
    }
}
