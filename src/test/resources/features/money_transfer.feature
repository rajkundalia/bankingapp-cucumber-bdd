Feature: Money Transfer
  As a bank customer
  I want to transfer money between accounts
  So that I can manage my finances effectively

  Background:
    Given the following accounts exist:
      | Account Number | Owner Name  | Initial Balance |
      | ACC-001        | John Smith  | 1000.00         |
      | ACC-002        | Jane Doe    | 500.00          |
      | ACC-003        | Sam Wilson  | 0.00            |

  Scenario: Successful money transfer between two accounts
    When I transfer $300.00 from account "ACC-001" to account "ACC-002"
    Then the balance of account "ACC-001" should be $700.00
    And the balance of account "ACC-002" should be $800.00

  Scenario: Transfer attempt with insufficient funds
    When I try to transfer $2000.00 from account "ACC-001" to account "ACC-002"
    Then the transfer should fail with message "Insufficient funds for transfer"
    And the balance of account "ACC-001" should remain $1000.00
    And the balance of account "ACC-002" should remain $500.00

# Single scenario with sequential transfers
  Scenario: Series of transfers across multiple accounts
    # First transfer
    When I transfer $200.00 from account "ACC-001" to account "ACC-003"
    Then the balance of account "ACC-001" should be $800.00
    And the balance of account "ACC-003" should be $200.00

    # Second transfer (builds on the state after first transfer)
    When I transfer $100.00 from account "ACC-002" to account "ACC-003"
    Then the balance of account "ACC-002" should be $400.00
    And the balance of account "ACC-003" should be $300.00

    # Third transfer (builds on the state after second transfer)
    When I transfer $50.00 from account "ACC-003" to account "ACC-001"
    Then the balance of account "ACC-003" should be $250.00
    And the balance of account "ACC-001" should be $850.00