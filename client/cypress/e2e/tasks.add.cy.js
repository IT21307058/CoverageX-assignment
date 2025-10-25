describe('Tasks - Add', () => {
    beforeEach(() => {
        // cy.resetBackend();
        cy.visit('/');
    });

    it('adds a task and shows it in the list', () => {
        cy.get('[data-testid="title-input"]').type('Buy milk');
        cy.get('[data-testid="description-input"]').type('2 liters');
        cy.get('[data-testid="add-btn"]').click();

        cy.contains('Task added successfully!').should('be.visible');
        cy.contains('Buy milk').should('exist');
        cy.contains('2 liters').should('exist');
    });

    it('validates empty form inputs', () => {
        cy.get('[data-testid="add-btn"]').click();
        cy.contains('Please fill in both the title and description.').should('be.visible');
    });
});
