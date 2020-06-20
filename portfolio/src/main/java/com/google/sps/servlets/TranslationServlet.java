// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;



import com.google.sps.servlets.DataServlet;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.sps.servlets.Comment;

@WebServlet("/translate")
public class TranslationServlet extends HttpServlet {
  String lang;  
@Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

     String languageCode = request.getParameter("languageCode");

    lang = languageCode;
    System.out.println(lang);
    
    response.sendRedirect("/index.html#contact");
  }
   @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query = new Query("portfolioComments");
    PreparedQuery results = datastore.prepare(query);
    List < Comment > comments = new ArrayList < > ();
  
    for (Entity entity: results.asIterable()) {
      String name = (String) entity.getProperty("name");
      String Usercomment = (String) entity.getProperty("message");


      Translate translate = TranslateOptions.getDefaultInstance().getService();
      Translation translationUsercomment =
        translate.translate(Usercomment, Translate.TranslateOption.targetLanguage(lang));
      String translatedUsercomment = translationUsercomment.getTranslatedText();


      Comment comment = new Comment(name, translatedUsercomment);
      comments.add(comment);
    }

    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().println(gson.toJson(comments));
  }
  
}
