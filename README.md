# BP Challenge

This project hast the following features:
- CRUD operations for customers.
- Account creation for customers.
- Deposit and withdraw operations for accounts.
- Report generation for accounts.

## Execution

The project has a `compose.yaml` and a `backend.Docerfile` files that can be used to run the entire project form a single command. The following command can be used to run the project:

```bash
$ docker compose up
```

## Endpoints

### Customers

| Name                   | Method | URL                                                       |
|------------------------|--------|-----------------------------------------------------------|
| Find By Id             | GET    | `/customers/{id}` |
| Create                 | POST   | `/customers`      |
| Update                 | PUT    | `/customers/{id}` |
| Partial Update         | PATCH  | `/customers/{id}` |
| Delete By Id           | DELETE | `/customers/{id}` |

### Accounts

| Name                   | Method | URL               |
|------------------------|--------|-------------------|
| Find By Id             | GET    | `/accounts/{id}`  |
| Create                 | POST   | `/accounts`       |

### Transactions

| Name                   | Method | URL                    | Query Params              |
|------------------------|--------|------------------------|---------------------------|
| Perform Transaction    | POST   | `/transactions`        | -                         |
| Generate Report        | GET    | `/transactions/report` | `customerId`, `dateRange` |

## Author

- David Reyes