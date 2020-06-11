package nl.rug.oop.grapheditor.controller.actions;

import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.model.edge.Edge;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

/**
 * Action to remove an edge
 */
public class CreateEdgeAction extends AbstractUndoableEdit {

    private GraphModel graphModel;
    private Edge edge;

    /**
     * Create a new add edge action
     * @param graphModel Graph Model
     * @param edge Edge
     */
    public CreateEdgeAction(GraphModel graphModel, Edge edge) {
        this.edge = edge;
        this.graphModel = graphModel;
    }

    /**
     * Remove the edge again
     * @throws CannotUndoException
     */
    @Override
    public void undo() throws CannotUndoException {
        graphModel.removeEdge(edge);
    }

    /**
     * Add the edge
     * @throws CannotRedoException
     */
    @Override
    public void redo() throws CannotRedoException {
        graphModel.addEdge(edge);
    }
}