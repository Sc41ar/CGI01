package org.example;

import lombok.*;

import java.awt.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Model {

    private Vector[] vertices;

    private int[][] edges;

    private int[][] faces;

    private Color color;

}
