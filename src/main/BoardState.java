/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User5587
 */
public class BoardState {

    public int Column;
    public List<char[][]> Boards;

    public BoardState() {
        Boards = new ArrayList<>();
    }
}
