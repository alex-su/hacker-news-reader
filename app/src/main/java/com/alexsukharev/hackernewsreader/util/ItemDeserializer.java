package com.alexsukharev.hackernewsreader.util;

import com.alexsukharev.hackernewsreader.model.Item;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ItemDeserializer implements JsonDeserializer<Item> {

    @Override
    public Item deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        final Gson gson = new Gson();
        final Item item = gson.fromJson(json, Item.class);
        final JsonObject jsonObject = json.getAsJsonObject();
        if (jsonObject.has("kids") && jsonObject.get("kids").isJsonArray()) {
            final List<Long> childrenIdList = gson.fromJson(jsonObject.get("kids"), new TypeToken<List<Long>>() {
            }.getType());
//            Observable.fromIterable(childrenIdList).map(Item::new).toList().subscribe(item::setChildren);
        }
        return item;
    }
}
