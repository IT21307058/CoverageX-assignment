describe('Tasks - Mark as DONE', () => {
    beforeEach(() => {
        // cy.resetBackend();
        cy.visit('/');
    });

    it('creates a task, marks it as DONE, then it disappears', () => {
        // Create via UI
        cy.get('[data-testid="title-input"]').type('To be done');
        cy.get('[data-testid="description-input"]').type('Mark me');
        cy.get('[data-testid="add-btn"]').click();
        cy.contains('To be done').should('exist');

        // Click Done on that card
        cy.contains('To be done')
            .parents('[data-testid^="task-"]')
            .within(() => cy.contains('Done').click());

        cy.contains('Task marked as completed!').should('be.visible');
        cy.contains('To be done').should('not.exist');
    });
});
