describe('Tasks - Load', () => {
    beforeEach(() => {
        cy.visit('/');
    });

    it('shows spinner then renders tasks (or empty state)', () => {
        cy.get('[role="status"]').should('exist');     
        cy.get('[role="status"]').should('not.exist'); 

        cy.get('body').then($body => {
            const hasTasks = $body.find('[data-testid^="task-"]').length > 0;
            if (hasTasks) {
                cy.get('[data-testid^="task-"]').should('have.length.at.least', 1);
            } else {
                cy.contains(/no tasks available/i).should('exist');
            }
        });
    });
});