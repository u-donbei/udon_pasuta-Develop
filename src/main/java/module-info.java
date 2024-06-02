/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
open module jp.co.yahoo.yu_w_main.udon_pasuta {
    requires java.net.http;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.media;
    requires javafx.graphics;
    requires javafx.swing;
    requires java.desktop;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires org.apache.commons.lang3;
    requires jp.co.yahoo.yu_w_main.yulib;
    requires ch.qos.logback.classic;
    requires ch.qos.logback.core;
    requires java.logging;
    requires org.slf4j;
    requires java.naming;
    requires java.management;
    requires org.apache.commons.compress;
    requires java.compiler;
    requires org.jetbrains.annotations;
    requires org.apache.commons.codec;
    requires lombok;

    exports jp.co.yahoo.yu_w_main.udon_pasuta.app to javafx.graphics, javafx.controls;
    exports jp.co.yahoo.yu_w_main.udon_pasuta.control;
    exports jp.co.yahoo.yu_w_main.udon_pasuta.panes;
    exports jp.co.yahoo.yu_w_main.udon_pasuta.panes.controller;

    exports jp.co.yahoo.yu_w_main.udon_pasuta.objects;
    exports jp.co.yahoo.yu_w_main.udon_pasuta.objects.block;
    exports jp.co.yahoo.yu_w_main.udon_pasuta.objects.block.field;
    exports jp.co.yahoo.yu_w_main.udon_pasuta.objects.character;
    exports jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.ally;
    exports jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.enemy;
    exports jp.co.yahoo.yu_w_main.udon_pasuta.objects.action;
    exports jp.co.yahoo.yu_w_main.udon_pasuta.dto;
    exports jp.co.yahoo.yu_w_main.udon_pasuta.constants;
    exports jp.co.yahoo.yu_w_main.udon_pasuta.misc;
    exports jp.co.yahoo.yu_w_main.udon_pasuta.plugin.texture;
    exports jp.co.yahoo.yu_w_main.udon_pasuta.objects.item;
    exports jp.co.yahoo.yu_w_main.udon_pasuta.objects.world;
    exports jp.co.yahoo.yu_w_main.udon_pasuta.logging;
    exports jp.co.yahoo.yu_w_main.udon_pasuta.plugin;
    exports jp.co.yahoo.yu_w_main.udon_pasuta.management;
    exports jp.co.yahoo.yu_w_main.udon_pasuta.save.deserialize to com.fasterxml.jackson.databind;
    exports jp.co.yahoo.yu_w_main.udon_pasuta.save.serialize to com.fasterxml.jackson.databind;

    exports jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.food;
    exports jp.co.yahoo.yu_w_main.udon_pasuta.managers;
    exports jp.co.yahoo.yu_w_main.udon_pasuta.constants.game;
//    exports jp.co.yahoo.yu_w_main.udon_pasuta.plugin to jp.co.yahoo.yu_w_main.udon_pasuta_test;
}