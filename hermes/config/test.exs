import Config

# We don't run a server during test. If one is required,
# you can enable the server option below.
config :hermes, HermesWeb.Endpoint,
  http: [ip: {127, 0, 0, 1}, port: 4002],
  secret_key_base: "ih8kVvQvN8kwC/EwMIgn7RXcOIgKwpBYYKtvyqySw/iKAnS9gGkxm6bn5Ojs9dIQ",
  server: false

# Print only warnings and errors during test
config :logger, level: :warning

# Initialize plugs at runtime for faster test compilation
config :phoenix, :plug_init_mode, :runtime
