package nl.rug.oop.grapheditor.controller.actions;

import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.model.node.Node;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

/**
 * Undoable Edit to remove a Node
 */
public class RemoveNodeAction extends AbstractUndoableEdit {

    private GraphModel graphModel;
    private Node node;

    /**
     * Create a new Action to create a node
     * @param graphModel Graph Model
     * @param node Node
     */
    public RemoveNodeAction(GraphModel graphModel, Node node) {
        this.graphModel = graphModel;
        this.node = node;
    }

    /**
     * Remove the Node from the graph Model
     * @throws CannotUndoException
     */
    @Override
    public void undo() throws CannotUndoException {
        graphModel.addNode(node);
    }

    /**
     * Add the node to the graph model
     * @throws CannotRedoException
     */
    @Override
    public void redo() throws CannotRedoException {
        graphModel.removeNode(node);
    }
}