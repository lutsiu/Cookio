package com.example.Cookio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CookioApplication {

	public static void main(String[] args) {
		SpringApplication.run(CookioApplication.class, args);

	}

}
/*
* [
    {
        "id": 8,
        "name": "Carrot",
        "type": "Vegetable",
        "calories": 41,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 9,
        "name": "Potato",
        "type": "Vegetable",
        "calories": 77,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 10,
        "name": "Tomato",
        "type": "Vegetable",
        "calories": 18,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 11,
        "name": "Cucumber",
        "type": "Vegetable",
        "calories": 15,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 12,
        "name": "Broccoli",
        "type": "Vegetable",
        "calories": 55,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 13,
        "name": "Spinach",
        "type": "Vegetable",
        "calories": 23,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 14,
        "name": "Onion",
        "type": "Vegetable",
        "calories": 40,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 15,
        "name": "Garlic",
        "type": "Vegetable",
        "calories": 149,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 16,
        "name": "Bell Pepper",
        "type": "Vegetable",
        "calories": 20,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 17,
        "name": "Zucchini",
        "type": "Vegetable",
        "calories": 17,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 18,
        "name": "Apple",
        "type": "Fruit",
        "calories": 52,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 19,
        "name": "Banana",
        "type": "Fruit",
        "calories": 89,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 20,
        "name": "Orange",
        "type": "Fruit",
        "calories": 47,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 21,
        "name": "Strawberry",
        "type": "Fruit",
        "calories": 33,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 22,
        "name": "Pineapple",
        "type": "Fruit",
        "calories": 50,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 23,
        "name": "Mango",
        "type": "Fruit",
        "calories": 60,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 24,
        "name": "Grapes",
        "type": "Fruit",
        "calories": 69,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 25,
        "name": "Blueberry",
        "type": "Fruit",
        "calories": 57,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 26,
        "name": "Watermelon",
        "type": "Fruit",
        "calories": 30,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 27,
        "name": "Cherry",
        "type": "Fruit",
        "calories": 50,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 28,
        "name": "Chicken Breast",
        "type": "Protein",
        "calories": 165,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 29,
        "name": "Egg",
        "type": "Protein",
        "calories": 155,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 30,
        "name": "Beef",
        "type": "Protein",
        "calories": 250,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 31,
        "name": "Pork",
        "type": "Protein",
        "calories": 242,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 32,
        "name": "Tofu",
        "type": "Protein",
        "calories": 76,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 33,
        "name": "Salmon",
        "type": "Protein",
        "calories": 208,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 34,
        "name": "Shrimp",
        "type": "Protein",
        "calories": 99,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 35,
        "name": "Lentils",
        "type": "Protein",
        "calories": 116,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 36,
        "name": "Peanuts",
        "type": "Protein",
        "calories": 567,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 37,
        "name": "Almonds",
        "type": "Protein",
        "calories": 579,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 38,
        "name": "Milk",
        "type": "Dairy",
        "calories": 42,
        "unit": "milliliters",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 39,
        "name": "Cheddar Cheese",
        "type": "Dairy",
        "calories": 403,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 40,
        "name": "Butter",
        "type": "Dairy",
        "calories": 717,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 41,
        "name": "Yogurt",
        "type": "Dairy",
        "calories": 59,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 42,
        "name": "Cream",
        "type": "Dairy",
        "calories": 292,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 43,
        "name": "Mozzarella",
        "type": "Dairy",
        "calories": 280,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 44,
        "name": "Rice",
        "type": "Grain",
        "calories": 130,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 45,
			"name": "Wheat Flour",
        "type": "Grain",
        "calories": 364,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 46,
        "name": "Oats",
        "type": "Grain",
        "calories": 389,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 47,
        "name": "Quinoa",
        "type": "Grain",
        "calories": 120,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 48,
        "name": "Barley",
        "type": "Grain",
        "calories": 354,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 49,
        "name": "Corn",
        "type": "Grain",
        "calories": 86,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 50,
        "name": "Sugar",
        "type": "Condiment",
        "calories": 387,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 51,
        "name": "Salt",
        "type": "Condiment",
        "calories": 0,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 52,
        "name": "Soy Sauce",
        "type": "Condiment",
        "calories": 53,
        "unit": "milliliters",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 53,
        "name": "Ketchup",
        "type": "Condiment",
        "calories": 112,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 54,
        "name": "Mustard",
        "type": "Condiment",
        "calories": 66,
        "unit": "grams",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 55,
        "name": "Vinegar",
        "type": "Condiment",
        "calories": 21,
        "unit": "milliliters",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 56,
        "name": "Olive Oil",
        "type": "Oil",
        "calories": 884,
        "unit": "milliliters",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 57,
        "name": "Coconut Oil",
        "type": "Oil",
        "calories": 862,
        "unit": "milliliters",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    },
    {
        "id": 58,
        "name": "Sunflower Oil",
        "type": "Oil",
        "calories": 884,
        "unit": "milliliters",
        "createdAt": "2024-11-23T13:55:11",
        "updatedAt": "2024-11-23T13:55:11"
    }
]*/
